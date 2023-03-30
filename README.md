# 环境搭建
- JDK：14.0.2
- 虚拟机：CentOS 7 64位
- tomcat：8.5.84
- 依赖管理工具：Maven-3.6.1 
- 数据库：MySQL-8.0.31、Redis-5.0.5
- RabbitMQ：3.8.5
# 项目简介
参考视频：https://www.bilibili.com/video/BV1sf4y1L7KE/?p=1&vd_source=3f02a65309de4b7b18d2bb0199f1cf4d
## 1. 登录模块
（1）手机号码参数校验：使用validation参数校验组件对参数LoginVo加注解@valid，在LoginVo中使用@NotNull定义非空、@Length定义长度和@Pattern定义正则表达式

（2）密码两次MD5加密：第一次加密防止在网络传输过程中以明文形式被截取，第二次加密防止数据库信息泄露被反推解码（加密两次加大反推密码的难度）
修改：可能第一次加密作用有限，可以通过解析前端js文件获得前端固定盐值和加密规则，因此容易通过碰撞解码。第二次数据库加密由于每个用户的盐值都不同，被碰撞解码难度较大。
修改为使用HTTPS协议传输请求。

（3）分布式session问题：登录成功后通过UUID生成token标记该用户，并向cookie中写入，客户端在随后的访问中都在cookie加上该token。redis中存储该token和用户信息。

## 2. 商品列表和商品详情页

（1）从MySQL数据库中读取商品信息，展示到商品列表页，点击商品详情会展示秒杀接口

（2）页面缓存和页面静态化：详见项目优化1

## 3. 秒杀接口
（1）判断库存以及用户是否重复抢购，若符合条件则减库存，并生成订单和秒杀订单（会出现超买超卖问题）。

（2）超买、超卖问题：详见项目优化2

（3）秒杀接口性能优化：详见项目优化3：

（4）秒杀接口安全优化：详见项目优化4

# 项目优化

## 1. 超买、超卖问题
（1）超买（重复下单问题）：通过创建唯一性索引，可以保证数据库表中每一行数据的唯一性。在秒杀商品订单表中以用户id列和商品id列组合建立一个唯一性索引就可以解决超买问题。在Redis中进行预减库存时，使用Redis原子性操作（使用decrby或者Lua脚本）进行减库存。
		 
（2）超卖：原始的方案是先查询数据库中库存数量是否大于0，大于零的话再执行减库存的操作，在高并发情况会出现超卖问题的。直接使用update（使用update在mysql中默认加排他锁）更新数据库，将对应商品的库存减一，如果更新成功的话说明还没有超卖（sql语句判断条件有stock>0）。
## 2. 秒杀接口性能优化

（1）页面静态化

使用纯html页面+Ajax请求数据后再填充页面，直接把页面缓存到浏览器上，当用户访问页面时，不需要和服务端进行交互。具体的数据使用ajax请求来获取，只更新极少量的数据就可。浏览器缓存就是把一个已经请求过的Web资源（如html页面，图片，js，数据等）拷贝一份副本储存在浏览器中。缓存会根据进来的请求保存输出内容的副本。当下一个请求来到的时候，如果是相同的URL，缓存会根据缓存机制决定是直接使用副本响应访问请求，还是向源服务器再次发送请求。

（2）秒杀商品信息存入redis

系统初始化后，将所有的秒杀的商品的库存信息放入redis中。让SeckillController实现InitializingBean接口，重写里面的afterPropertiesSet方法

（3）使用redis预减库存

使用redis+lua脚本实现原子操作。使用redis预减库存，如果库存不足，直接返回错误。

（4）进一步减少对redis 的访问：内存标记

定义一个HashMap存放秒杀商品是否stock<0 的布尔值。

## 3. 秒杀接口安全优化

（1）接口隐藏且唯一：

未到秒杀开始时间时，隐藏秒杀接口地址，避免脚本直接确定地址。页面中秒杀按钮是请求获取秒杀地址，因此黄牛大量请求时也只是获取地址，并不能完成秒杀。
在秒杀之前要随机生成一个path，然后和用户ID一起存入redis，在执行秒杀的时候再从redis中取Path进行验证，这样每个用户的秒杀地址都不同且唯一。

（2）接口限流

配合redis消亡时间设置每个用户在一定时间内最大可请求次数。

（3）使用RabbitMQ削峰

如果用户符合当前秒杀条件，则将秒杀请求发送到队列，然后接收端会从消息队列中取出消息进行异步下单操作。最后前端轮询查询秒杀结果。这样可以实现用户异步操作，同时减少数据库的访问压力。

（4）使用验证码分流

使用开源的验证码项目进行分流（不同用户完成验证时间不相同），将验证码结果存入redis中后续进行验证。

# 常见问题
## 1. Redis中存了什么？

用户信息（分布式session）、用户访问次数（用户限流）、用户订单信息（是否重复抢购）、秒杀商品库存（配合lua脚本判断）、唯一的秒杀地址（秒杀地址正确且其他校验通过，则使用消息队列进行秒杀）、验证码结果（成功则获取秒杀地址）

## 2. 有哪些解决分布式session问题的方式？
（1）客户端存储：直接将信息存储到cookie中，但是安全性较差。

（2）sesssion复制：任何一个服务器上的session发生改变时，该服务器会把这个session广播给其他服务器。

（3）共享session：将session信息存储到缓存中间件（如Redis）来统一进行管理。

（4）Nginx分配：使用Nginx代理，每个请求按照IP进行固定分配，这样来自同一IP的请求会固定访问一个后台服务器上。

## 3. 有哪些限流方式？

（1）计数限流：

设置请求阈值，每次请求来的时候判断计数器的值，如果超过阈值就拒绝。每接受一个请求计数器就加一，每处理完毕一个请求计数器就减一

（2）固定窗口限流：

相比计数限流主要多了时间窗口的概念，计数器每过一个时间窗口就重置。例如在Redis中结合消亡时间设置访问阈值

（3）滑动窗口限流：

相比固定窗口可以保证任意时间窗口内都不会超过阈值

缺点：上述1，2，3都存在短时间内集中流量的突击的问题（例如最开始部分和固定窗口临界部分）

（4）漏桶算法：

将收到的请求放置于桶中，若桶内请求量满了拒绝请求。定速从桶内拿请求进行处理。

优点：能够解决突发流量问题  缺点：(a)面对突发请求时，处理速度仍与平时一样  (b)请求可能在桶中存放时间长，对用户来说业务处理延迟较高

（5）令牌桶算法：

定速向桶内放入令牌，请求只有拿到了令牌才能被服务器处理。适用于应对突发流量

## 4. 项目是否存在缓存一致性问题？

（1）本项目可能出现缓存一致性问题的地方是Redis预减库存，但是Redis在这里起到的是过滤请求的作用，真正在减库存时会在数据库中判断。因此不需要考虑缓存一致性问题。

（2）常见的解决缓存一致性问题的方法有延时双删，但是针对于经常被更新的数据（如这里Redis中存储的库存数量），使用延时双删会导致缓存数据被频繁删除，反而起不到Redis缓存提高访问速度的作用。

## 5. 项目性能的瓶颈在哪里？

猜测：redis性能、消息队列性能、服务器硬件资源性能

## 6. 项目后续优化方向？

（1）用户限流：使用计数限流（固定窗口限流）存在集中流量突击的问题，后续可使用令牌桶算法进行用户限流

（2）项目暂未解决缓存一致性问题：
