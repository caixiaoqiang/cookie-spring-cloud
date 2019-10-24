- META-INF 表示bean自动注入，需在使用的类上配合使用@ComponentScan
- Spring Cloud在外部请求进来时，Ribbon默认的Rule为ZoneAvoidanceRule，此时的版本控制，只需重写ZoneAvoidanceRule类的Choose方法，返回自身所需的相应服务版本，同时需要通过@RibbonClients指定ribbon运行的rule
- Spring Cloud 在内部服务与服务之间调用时，无论是RestTemplate还是Feign，最后也都会走ribbon的负载均衡算法，此时需要需要让Zuul添加线程变量到消息头中，然后自定义HandlerInterceptorAdapter拦截器，使之在到达服务之前将请求头中的信息存入到线程变量HystrixRequestVariableDefault中。
- 在使用该版本控制时，除了api所在项目外，被feign调用的项目也应该同时引入该项目，且使用注解@ComponentScan进行bean的自动注入，否则在feign请求时，无法进行版本选择
- api上需使用zuul.routes.sensitiveHeaders=Cookie,Set-Cookie,Authorization 防止消息头被过滤