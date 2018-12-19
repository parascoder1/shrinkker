package DBRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;

@Repository
public class URLStorage {
	
	private final Jedis jedis;
	private final String idKey;
	private final String urlKey;
	
	private static final Logger log = LoggerFactory.getLogger(URLStorage.class);
	
	public URLStorage() {
		this.jedis = new Jedis();
		this.idKey = "id";
		this.urlKey = "url:";
	}
	
	public URLStorage(Jedis jedis, String idkey, String urlkey) {
		this.jedis = jedis;
		this.idKey = idkey;
		this.urlKey = urlkey;
	}
	
	public Long increamentId() {
		Long id = jedis.incr(idKey);
		log.info("INSIDE incrementId :: Incrementing id : " + (id - 1));
		return id - 1;
	}
	
	public void addUrl(String key, String longUrl) {
		log.info("INSIDE addurl :: Saving : " + longUrl + " "  + key);
		jedis.hset(urlKey, key, longUrl);	
	}
	
	public String getRealUrl(Long id) throws Exception {
		log.info("INSIDE getRealUrl :: Retrieving at id = " + id);
		String url = jedis.hget(urlKey,"url:" + id);
		log.info("INSIDE getRealUrl :: Retrieved " + url + " at " + id );
		if(url == null) {
			throw new Exception("URL at key" + id +" does not exist");
		}
		return url;
	}
	
}
