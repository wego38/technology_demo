package redis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.SortingParams;

public class RedisClient {

	private Jedis jedis=new Jedis("180.76.176.234", 6379);// 非切片额客户端连接
	private JedisPool jedisPool;// 非切片连接池
	private ShardedJedis shardedJedis;// 切片额客户端连接
	private ShardedJedisPool shardedJedisPool;// 切片连接池

	public RedisClient() {
//		jedis.auth("nil");
//		initialPool();
//		initialShardedPool();
//		shardedJedis = shardedJedisPool.getResource();
//		jedis = jedisPool.getResource();

	}

	public static void main(String[] args) {
		RedisClient client=new RedisClient();
		client.KeyOperate();
		
	}
	
	/**
	 * 初始化非切片池
	 */
	private void initialPool() {
		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(20);
		config.setMaxIdle(5);
		config.setMaxWaitMillis(1000l);
		config.setTestOnBorrow(false);

		jedisPool = new JedisPool(config, "180.76.176.234", 6379);
	}

	/**
	 * 初始化切片池
	 */
	private void initialShardedPool() {
		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(5);
		config.setMaxWaitMillis(10000);
		config.setTestOnBorrow(false);
		// slave链接
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo("180.76.176.234", 6379, "root"));

		// 构造池
		shardedJedisPool = new ShardedJedisPool(config, shards);
	}

	public void show() {
		KeyOperate();
		StringOperate();
		ListOperate();
		SetOperate();
		SortedSetOperate();
		HashOperate();
		jedisPool.returnResource(jedis);
		shardedJedisPool.returnResource(shardedJedis);
	}

	public void KeyOperate() {
		jedis.set("name", "xinxin");// 向key-->name中放入了value-->xinxin
		System.out.println(jedis.get("name"));// 执行结果：xinxin
		jedis.append("name", " is my lover"); // 拼接
		System.out.println(jedis.get("name"));
	}

	private void StringOperate() {

	}

	private void ListOperate() {

	}

	private void SetOperate() {

	}

	private void SortedSetOperate() {

	}

	private void HashOperate() {

	}

	
}