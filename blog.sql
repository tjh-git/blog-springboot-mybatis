/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 80021
Source Host           : localhost:3306
Source Database       : blog

Target Server Type    : MYSQL
Target Server Version : 80021
File Encoding         : 65001

Date: 2021-03-24 17:46:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES ('25');

-- ----------------------------
-- Table structure for t_blog
-- ----------------------------
DROP TABLE IF EXISTS `t_blog`;
CREATE TABLE `t_blog` (
  `id` bigint NOT NULL,
  `appreciation` bit(1) NOT NULL,
  `commentabled` bit(1) NOT NULL,
  `content` longtext,
  `create_time` datetime(6) DEFAULT NULL,
  `first_picture` varchar(255) DEFAULT NULL,
  `flag` varchar(255) DEFAULT NULL,
  `published` bit(1) NOT NULL,
  `recommend` bit(1) NOT NULL,
  `share_statement` bit(1) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `views` int DEFAULT NULL,
  `type_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK292449gwg5yf7ocdlmswv9w4j` (`type_id`),
  KEY `FK8ky5rrsxh01nkhctmo7d48p82` (`user_id`),
  CONSTRAINT `FK292449gwg5yf7ocdlmswv9w4j` FOREIGN KEY (`type_id`) REFERENCES `t_type` (`id`),
  CONSTRAINT `FK8ky5rrsxh01nkhctmo7d48p82` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_blog
-- ----------------------------
INSERT INTO `t_blog` VALUES ('3', '', '', '## 3、框架搭建 ##\r\n\r\n> [IDEA下载 https://www.jetbrains.com/idea/](https://www.jetbrains.com/idea/)\r\n\r\n### 3.1 构建与配置 ###\r\n\r\n**1、引入Spring Boot模块：**\r\n\r\n- web\r\n- Thymeleaf\r\n- JPA\r\n- MySQL\r\n- Aspects\r\n- DevTools\r\n\r\n**2、application.yml配置**\r\n\r\n- 使用 thymeleaf 3\r\n\r\n  pom.xml:\r\n\r\n```yml\r\n<thymeleaf.version>3.0.2.RELEASE</thymeleaf.version>\r\n<thymeleaf-layout-dialect.version>2.1.1</thymeleaf-layout-dialect.version>\r\n\r\n\r\nspring:\r\n  thymeleaf:\r\n    mode: HTML\r\n```\r\n\r\n- 数据库连接配置\r\n\r\n```yml\r\nspring:\r\n  datasource:\r\n      driver-class-name: com.mysql.cj.jdbc.Driver\r\n      url: jdbc:mysql://localhost:3306/blog?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8\r\n      username: root\r\n      password: 123456\r\n  jpa:\r\n	hibernate:\r\n		ddl-auto: update\r\n		show-sql: true\r\n```\r\n\r\n- 日志配置\r\n\r\n  application.yml:\r\n\r\n```yaml\r\nlogging:\r\n		  level:\r\n		    root: info\r\n		    com.imcoding: debug\r\n		  file: log/imcoding.log\r\n```\r\n\r\n- application.yml\r\n\r\n  ```yml\r\n  spring:\r\n    thymeleaf:\r\n      mode: HTML\r\n    datasource:\r\n        driver-class-name: com.mysql.cj.jdbc.Driver\r\n        url: jdbc:mysql://localhost:3306/blog?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8\r\n        username: root\r\n        password: 123456\r\n    jpa:\r\n      hibernate:\r\n        ddl-auto: update\r\n      show-sql: true\r\n  logging:\r\n    level:\r\n      root: info\r\n      com.tjh: debug\r\n    file:\r\n      path: log/blog.log\r\n  ```\r\n\r\n\r\n\r\n\r\n\r\n logback-spring.xml：\r\n\r\n```xml\r\n<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n		<configuration>\r\n		    <!--包含Spring boot对logback日志的默认配置-->\r\n		    <include resource=\"org/springframework/boot/logging/logback/defaults.xml\" />\r\n		    <property name=\"LOG_FILE\" value=\"${LOG_FILE:-${LOG_PATH:-${LOG_TEMP:-${java.io.tmpdir:-/tmp}}}/spring.log}\"/>\r\n		    <include resource=\"org/springframework/boot/logging/logback/console-appender.xml\" />\r\n		\r\n		    <!--重写了Spring Boot框架 org/springframework/boot/logging/logback/file-appender.xml 配置-->\r\n		    <appender name=\"TIME_FILE\"\r\n		              class=\"ch.qos.logback.core.rolling.RollingFileAppender\">\r\n		        <encoder>\r\n		            <pattern>${FILE_LOG_PATTERN}</pattern>\r\n		        </encoder>\r\n		        <file>${LOG_FILE}</file>\r\n		        <rollingPolicy class=\"ch.qos.logback.core.rolling.TimeBasedRollingPolicy\">\r\n		            <fileNamePattern>${LOG_FILE}.%d{yyyy-MM-dd}.%i</fileNamePattern>\r\n		            <!--保留历史日志一个月的时间-->\r\n		            <maxHistory>30</maxHistory>\r\n		            <!--\r\n		            Spring Boot默认情况下，日志文件10M时，会切分日志文件,这样设置日志文件会在100M时切分日志\r\n		            -->\r\n		            <timeBasedFileNamingAndTriggeringPolicy class=\"ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP\">\r\n		                <maxFileSize>10MB</maxFileSize>\r\n		            </timeBasedFileNamingAndTriggeringPolicy>\r\n		\r\n		        </rollingPolicy>\r\n		    </appender>\r\n		\r\n		    <root level=\"INFO\">\r\n		        <appender-ref ref=\"CONSOLE\" />\r\n		        <appender-ref ref=\"TIME_FILE\" />\r\n		    </root>\r\n		\r\n		</configuration>\r\n		<!--\r\n		    1、继承Spring boot logback设置（可以在appliaction.yml或者application.properties设置logging.*属性）\r\n		    2、重写了默认配置，设置日志文件大小在100MB时，按日期切分日志，切分后目录：\r\n		\r\n		        my.2017-08-01.0   80MB\r\n		        my.2017-08-01.1   10MB\r\n		        my.2017-08-02.0   56MB\r\n		        my.2017-08-03.0   53MB\r\n		        ......\r\n		-->\r\n\r\n```\r\n\r\n- 生产环境与开发环境配置\r\n  - application-dev.yml\r\n  - application-pro.yml\r\n\r\n### 3.2 异常处理 ###\r\n\r\n**1、定义错误页面**\r\n\r\n- 404\r\n- 500\r\n- error\r\n\r\n**2、全局处理异常**\r\n\r\n统一处理异常：\r\n\r\n```java\r\n@ControllerAdvice\r\n		public class ControllerExceptionHandler {\r\n		\r\n		    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);\r\n		    /**\r\n		     * 异常处理\r\n		     * @param request\r\n		     * @param e\r\n		     * @return\r\n		     */\r\n		    @ExceptionHandler({Exception.class})\r\n		    public ModelAndView handleException(HttpServletRequest request, Exception e) throws Exception {\r\n		\r\n		        logger.error(\"Request URL : {} , Exception : {}\", request.getRequestURL(), e);\r\n		\r\n		        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {\r\n		            throw e;\r\n		        }\r\n		        ModelAndView mav = new ModelAndView();\r\n		        mav.addObject(\"url\", request.getRequestURL());\r\n		        mav.addObject(\"exception\", e);\r\n		        mav.setViewName(\"error/error\");\r\n		\r\n		        return mav;\r\n		    }\r\n}\r\n```\r\n\r\n错误页面异常信息显示处理：\r\n\r\n### thymeleft头引用 ###\r\n\r\n```HTML\r\nxmlns:th=\"http://www.thymeleaf.org\"\r\n```\r\n\r\n```html\r\n<div>\r\n		    <div th:utext=\"\'&lt;!--\'\" th:remove=\"tag\"></div>\r\n		    <div th:utext=\"\'Failed Request URL : \' + ${url}\" th:remove=\"tag\"></div>\r\n		    <div th:utext=\"\'Exception message : \' + ${exception.message}\" th:remove=\"tag\"></div>\r\n		    <ul th:remove=\"tag\">\r\n		        <li th:each=\"st : ${exception.stackTrace}\" th:remove=\"tag\"><span th:utext=\"${st}\" th:remove=\"tag\"></span></li>\r\n		    </ul>\r\n		    <div th:utext=\"\'--&gt;\'\" th:remove=\"tag\"></div>\r\n</div>\r\n```\r\n\r\n**3、资源找不到异常**\r\n\r\n```java\r\n@ResponseStatus(HttpStatus.NOT_FOUND)\r\n		public class NotFoundExcepiton extends RuntimeException {\r\n		\r\n		    public NotFoundExcepiton() {\r\n		    }\r\n		\r\n		    public NotFoundExcepiton(String message) {\r\n		        super(message);\r\n		    }\r\n		\r\n		    public NotFoundExcepiton(String message, Throwable cause) {\r\n		        super(message, cause);\r\n		    }\r\n}\r\n```\r\n\r\n### 3.3 日志处理 ###\r\n\r\n**1、记录日志内容**\r\n\r\n- 请求 url\r\n- 访问者 ip\r\n- 调用方法 classMethod\r\n- 参数 args\r\n- 返回内容\r\n\r\n**2、记录日志类：**\r\n\r\n```java\r\n@Aspect\r\n		@Component\r\n		public class LogAspect {\r\n		\r\n		    private final Logger logger = LoggerFactory.getLogger(this.getClass());\r\n		\r\n		    /**\r\n		     * 定义切面\r\n		     */\r\n		    @Pointcut(\"execution(* com.imcoding.web.*.*(..))\")\r\n		    public void log() {\r\n		    }\r\n		\r\n		    @Before(\"log()\")\r\n		    public void doBefore(JoinPoint joinPoint) {\r\n		        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();\r\n		        HttpServletRequest request = attributes.getRequest();\r\n		        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + \".\" + joinPoint.getSignature().getName();\r\n		        ReqeustLog reqeustLog = new ReqeustLog(\r\n		                request.getRequestURL().toString(),\r\n		                request.getRemoteAddr(),\r\n		                classMethod,\r\n		                joinPoint.getArgs()\r\n		        );\r\n		        logger.info(\"Rquest  ----- {}\",reqeustLog);\r\n		    }\r\n		\r\n		    @After(\"log()\")\r\n		    public void doAfter() {\r\n		        //logger.info(\"---------- doAfter 2 ----------\");\r\n		    }\r\n		\r\n		    @AfterReturning(returning = \"result\",pointcut = \"log()\")\r\n		    public void doAtfertRturning(Object result) {\r\n		        logger.info(\"Return ------ {}\",result );\r\n		    }\r\n		\r\n		\r\n		    private class ReqeustLog {\r\n		        private String url;\r\n		        private String ip;\r\n		        private String classMethod;\r\n		        private Object[] args;\r\n		\r\n		        public ReqeustLog(String url, String ip, String classMethod, Object[] args) {\r\n		            this.url = url;\r\n		            this.ip = ip;\r\n		            this.classMethod = classMethod;\r\n		            this.args = args;\r\n		        }\r\n		\r\n		        @Override\r\n		        public String toString() {\r\n		            return \"ReqeustLog{\" +\r\n		                    \"url=\'\" + url + \'\\\'\' +\r\n		                    \", ip=\'\" + ip + \'\\\'\' +\r\n		                    \", classMethod=\'\" + classMethod + \'\\\'\' +\r\n		                    \", args=\" + Arrays.toString(args) +\r\n		                    \'}\';\r\n		        }\r\n		    }\r\n}\r\n```\r\n\r\n###  ###', '2021-03-21 07:55:45.314000', 'https://images.unsplash.com/photo-1616243641306-288d5077dbe4?ixid=MXwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwyfHx8ZW58MHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=450', '转载', '', '', '', 'markdown', '2021-03-21 07:55:45.314000', '2', '3', '1', '暂无');
INSERT INTO `t_blog` VALUES ('4', '', '', '# Spring Boot开发小而美的个人博客 #\r\n\r\n\r\n\r\n**个人博客功能：**\r\n\r\n![img](https://imgconvert.csdnimg.cn/aHR0cHM6Ly93czIuc2luYWltZy5jbi9sYXJnZS8wMDZ0S2ZUY2d5MWZrN20yN2hibjRqMzFkczB5Y2RucC5qcGc?x-oss-process=image/format,png)\r\n\r\n**技术组合：**\r\n\r\n- 后端：Spring Boot + JPA + thymeleaf模板\r\n- 数据库：MySQL\r\n- 前端UI：Semantic UI框架\r\n\r\n**工具与环境：**\r\n\r\n- IDEA\r\n- Maven 3\r\n- JDK 8\r\n- Axure RP 8\r\n\r\n**课程内容模块：**\r\n\r\n- 需求分析与功能规划\r\n- 页面设计与开发\r\n- 技术框架搭建\r\n- 后端管理功能实现\r\n- 前端管理功能实现\r\n\r\n**你能学得什么？**\r\n\r\n- 基于Spring Boot的完整全栈式的开发套路\r\n- Semantic UI框架的使用\r\n- 一套博客系统的源代码与设计', '2021-03-21 07:58:44.105000', 'https://picsum.photos/id/1/800/450', '翻译', '', '', '', '测试博客', '2021-03-21 07:58:44.105000', '1', '2', '1', '暂无');
INSERT INTO `t_blog` VALUES ('5', '\0', '\0', '暂无\r\n修改', '2020-03-21 07:58:44.105000', 'https://picsum.photos/id/1/800/450', '翻译', '\0', '', '', '草稿修改', '2020-03-21 08:49:45.594000', '2', '5', '1', '暂无');
INSERT INTO `t_blog` VALUES ('6', '\0', '', '就这', '2021-03-21 08:04:48.036000', 'https://picsum.photos/id/1/800/450', '原创', '', '\0', '', '发布', '2021-03-21 08:04:48.036000', '0', '6', '1', '暂无');
INSERT INTO `t_blog` VALUES ('9', '\0', '', '多标签测试', '2021-03-21 07:58:44.105000', 'https://picsum.photos/id/1/800/450', '原创', '', '', '\0', '多标签测试', '2021-03-21 11:41:40.155000', '28', '6', '1', '暂无，OK');
INSERT INTO `t_blog` VALUES ('11', '\0', '\0', '标签添加', '2021-03-21 11:27:56.014000', 'https://picsum.photos/id/1/800/450', '转载', '\0', '\0', '', '标签添加', '2021-03-21 11:27:56.014000', '1', '6', '1', '暂无');

-- ----------------------------
-- Table structure for t_blog_tags
-- ----------------------------
DROP TABLE IF EXISTS `t_blog_tags`;
CREATE TABLE `t_blog_tags` (
  `blogs_id` bigint NOT NULL,
  `tags_id` bigint NOT NULL,
  KEY `FK5feau0gb4lq47fdb03uboswm8` (`tags_id`),
  KEY `FKh4pacwjwofrugxa9hpwaxg6mr` (`blogs_id`),
  CONSTRAINT `FK5feau0gb4lq47fdb03uboswm8` FOREIGN KEY (`tags_id`) REFERENCES `t_tag` (`id`),
  CONSTRAINT `FKh4pacwjwofrugxa9hpwaxg6mr` FOREIGN KEY (`blogs_id`) REFERENCES `t_blog` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_blog_tags
-- ----------------------------
INSERT INTO `t_blog_tags` VALUES ('3', '1');
INSERT INTO `t_blog_tags` VALUES ('4', '1');
INSERT INTO `t_blog_tags` VALUES ('6', '1');
INSERT INTO `t_blog_tags` VALUES ('5', '1');
INSERT INTO `t_blog_tags` VALUES ('5', '8');
INSERT INTO `t_blog_tags` VALUES ('11', '10');
INSERT INTO `t_blog_tags` VALUES ('9', '1');
INSERT INTO `t_blog_tags` VALUES ('9', '7');
INSERT INTO `t_blog_tags` VALUES ('9', '8');
INSERT INTO `t_blog_tags` VALUES ('9', '10');
INSERT INTO `t_blog_tags` VALUES ('9', '12');
INSERT INTO `t_blog_tags` VALUES ('9', '13');

-- ----------------------------
-- Table structure for t_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `id` bigint NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `blog_id` bigint DEFAULT NULL,
  `parent_comment_id` bigint DEFAULT NULL,
  `admin_comment` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKke3uogd04j4jx316m1p51e05u` (`blog_id`),
  KEY `FK4jj284r3pb7japogvo6h72q95` (`parent_comment_id`),
  CONSTRAINT `FK4jj284r3pb7japogvo6h72q95` FOREIGN KEY (`parent_comment_id`) REFERENCES `t_comment` (`id`),
  CONSTRAINT `FKke3uogd04j4jx316m1p51e05u` FOREIGN KEY (`blog_id`) REFERENCES `t_blog` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_comment
-- ----------------------------
INSERT INTO `t_comment` VALUES ('15', '/images/ava.png', '我系个好人', '2021-03-22 13:09:19.205000', 'haoren@qq.com', '好人', '9', null, '\0');
INSERT INTO `t_comment` VALUES ('16', '/images/ava.png', '测试1', '2021-03-22 13:09:53.350000', 'haoren@qq.com', '测试', '9', null, '\0');
INSERT INTO `t_comment` VALUES ('17', '/images/ava.png', '你不是', '2021-03-22 13:10:10.585000', 'huairen@qq.com', '坏人', '9', '15', '\0');
INSERT INTO `t_comment` VALUES ('18', '/images/ava.png', 'xioabai', '2021-03-22 13:12:38.119000', '123@1.c', 'xioabai', '9', null, '\0');
INSERT INTO `t_comment` VALUES ('19', '/images/pangluozi.jpg', 'wu', '2021-03-22 13:15:36.664000', 'w@w.w', 'w', '9', null, '\0');
INSERT INTO `t_comment` VALUES ('20', '/images/ava.png', '胖骡子', '2021-03-22 13:27:02.914000', '121@qq.con', '胖骡子', '9', null, '\0');
INSERT INTO `t_comment` VALUES ('21', '/images/pangluozi.jpg', '回复', '2021-03-23 05:18:59.607000', '123@1.c', '回复测试', '9', '17', '\0');
INSERT INTO `t_comment` VALUES ('22', '/images/pangluozi.jpg', 'uuu\n', '2021-03-23 05:28:11.100000', '11111@qq.com', 'yyy', '4', null, '\0');
INSERT INTO `t_comment` VALUES ('23', 'https://picsum.photos/id/1025/800/450', '管理员的信息', '2021-03-23 05:52:22.943000', '2637185783@qq.com', '质疑', '9', null, '');
INSERT INTO `t_comment` VALUES ('24', 'https://picsum.photos/id/1025/800/450', '傻逼', '2021-03-23 05:52:32.475000', '2637185783@qq.com', '质疑', '9', '19', '');

-- ----------------------------
-- Table structure for t_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag` (
  `id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_tag
-- ----------------------------
INSERT INTO `t_tag` VALUES ('1', 'sql');
INSERT INTO `t_tag` VALUES ('7', 'bq1');
INSERT INTO `t_tag` VALUES ('8', '标签2');
INSERT INTO `t_tag` VALUES ('10', '标签添加');
INSERT INTO `t_tag` VALUES ('12', '标签添加2');
INSERT INTO `t_tag` VALUES ('13', '标签测试1');

-- ----------------------------
-- Table structure for t_type
-- ----------------------------
DROP TABLE IF EXISTS `t_type`;
CREATE TABLE `t_type` (
  `id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_type
-- ----------------------------
INSERT INTO `t_type` VALUES ('1', '分类1');
INSERT INTO `t_type` VALUES ('2', '分类2');
INSERT INTO `t_type` VALUES ('3', 'java');
INSERT INTO `t_type` VALUES ('4', 'mysql');
INSERT INTO `t_type` VALUES ('5', '清单');
INSERT INTO `t_type` VALUES ('6', '前端');
INSERT INTO `t_type` VALUES ('7', '全栈');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `type` int DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'https://picsum.photos/id/1025/800/450', '2021-03-21 12:49:49.000000', '2637185783@qq.com', '质疑', 'e10adc3949ba59abbe56e057f20f883e', '1', '2021-03-21 12:50:06.000000', 'admin');
