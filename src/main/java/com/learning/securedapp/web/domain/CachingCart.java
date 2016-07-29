package com.learning.securedapp.web.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CachingCart extends Cart {
	private static final long serialVersionUID = 1L;

	public CachingCart() {
		super();
		loadCache();
	}

	@Override
	public OrderLines getOrderLines() {
		loadCache();
		return super.getOrderLines();
	}

	@Override
	public Cart add(OrderLine orderLine) {
		withSyncCache(() -> super.add(orderLine), true);
		return this;
	}

	@Override
	public Cart remove(Set<Integer> lineNo) {
		withSyncCache(() -> super.remove(lineNo), true);
		return this;
	}

	@Override
	public Cart clear() {
		withSyncCache(super::clear, true);
		return this;
	}

	Cache getCache() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		ApplicationContext context = RequestContextUtils.findWebApplicationContext(attributes.getRequest());
		CacheManager cacheManager = context.getBean(CacheManager.class);
		Cache cache = cacheManager.getCache("orderLines");
		return cache;
	}

	/**
	 * OrderLines to run the synchronization while processing and cache
	 *
	 * @param action
	 *            The main Processing
	 * @param save
	 *            After the processing , whether you want to save in the cache
	 */
	void withSyncCache(Runnable action, boolean save) {
		Cache cache = getCache();
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		OrderLines orderLines = cache.get(username, OrderLines.class);
		if (orderLines != null) {
			// Read from the cache
			log.debug("load {} -> {}", username, orderLines);
			List<OrderLine> lines = new ArrayList<>(orderLines.getList()); // copy
			super.getOrderLines().getList().clear();
			super.getOrderLines().getList().addAll(lines);
		}
		// processing
		action.run();
		if (save) {
			// Stored in the cache
			if (log.isDebugEnabled()) {
				log.debug("save {} -> {}", username, super.getOrderLines());
			}
			cache.put(username, super.getOrderLines());
		}
	}

	void loadCache() {
		withSyncCache(() -> {
		}, false);
	}
}
