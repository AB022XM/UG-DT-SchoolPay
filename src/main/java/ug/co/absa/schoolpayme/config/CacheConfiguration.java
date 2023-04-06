package ug.co.absa.schoolpayme.config;

import java.time.Duration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, ug.co.absa.schoolpayme.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, ug.co.absa.schoolpayme.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, ug.co.absa.schoolpayme.domain.User.class.getName());
            createCache(cm, ug.co.absa.schoolpayme.domain.Authority.class.getName());
            createCache(cm, ug.co.absa.schoolpayme.domain.User.class.getName() + ".authorities");
            createCache(cm, ug.co.absa.schoolpayme.domain.Biller.class.getName());
            createCache(cm, ug.co.absa.schoolpayme.domain.Biller.class.getName() + ".paymentEnts");
            createCache(cm, ug.co.absa.schoolpayme.domain.Biller.class.getName() + ".paybills");
            createCache(cm, ug.co.absa.schoolpayme.domain.Student.class.getName());
            createCache(cm, ug.co.absa.schoolpayme.domain.Student.class.getName() + ".contactInfos");
            createCache(cm, ug.co.absa.schoolpayme.domain.Customer.class.getName());
            createCache(cm, ug.co.absa.schoolpayme.domain.BillerCatergory.class.getName());
            createCache(cm, ug.co.absa.schoolpayme.domain.ContactInfo.class.getName());
            createCache(cm, ug.co.absa.schoolpayme.domain.Address.class.getName());
            createCache(cm, ug.co.absa.schoolpayme.domain.School.class.getName());
            createCache(cm, ug.co.absa.schoolpayme.domain.PaymentEnt.class.getName());
            createCache(cm, ug.co.absa.schoolpayme.domain.PaymentEnt.class.getName() + ".associatedFees");
            createCache(cm, ug.co.absa.schoolpayme.domain.ValidateCustomerById.class.getName());
            createCache(cm, ug.co.absa.schoolpayme.domain.Paybill.class.getName());
            createCache(cm, ug.co.absa.schoolpayme.domain.AssociatedFees.class.getName());
            createCache(cm, ug.co.absa.schoolpayme.domain.PaymentChannelEnt.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
