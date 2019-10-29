package com.cookie.filter;

import com.alibaba.fastjson.JSONObject;
import com.cookie.CoreHeaderInterceptor;
import com.cookie.CoreInitLabel;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author cxq
 * @date 2019/03/12
 */
@Component
public class GrayFilter extends ZuulFilter {

	private String testUuids = "cookie,andy";

	@Value("${info.profile}")
	private String profile ;

	private final static Logger logger = LoggerFactory.getLogger(GrayFilter.class);
	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return 1000;
	}

	@Override
	public boolean shouldFilter() {
		System.out.println(profile);
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		String token = request.getHeader("token");
		String labelVersion = "test";
		if (StringUtils.isNotEmpty(token) && testUuids.contains(token)){
			labelVersion = "product";
		}
		// 获取指定版本
		Map<String, String> serverMap = new CoreInitLabel().initLabel(labelVersion);
		// 版本选择
		ctx.addZuulRequestHeader(CoreHeaderInterceptor.HEADER_LABEL, JSONObject.toJSONString(serverMap));

		return true;
	}

	@Override
	public Object run() {

		return  null ;
	}
}
