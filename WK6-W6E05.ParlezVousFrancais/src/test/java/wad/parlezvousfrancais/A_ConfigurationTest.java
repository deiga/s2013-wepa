package wad.parlezvousfrancais;

import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

@Points("W6E05.1")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/spring-base.xml"})
public class A_ConfigurationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testIfMessageSourceIsConfigured() {

        MessageSource source = null;

        try {
            source = (MessageSource) applicationContext.getBean(MessageSource.class);
        } catch (Exception e) {
            Assert.fail("Verify that you have configured ReloadableResourceBundleMessageSource in Spring ApplicationContext. Error: " + e.toString());
        }

        Assert.assertTrue("Verify that you have configured ReloadableResourceBundleMessageSource in Spring ApplicationContext.", source.getClass().equals(ReloadableResourceBundleMessageSource.class));
    }

    @Test
    public void testIfCookieLocaleResolverIsConfigured() {

        LocaleResolver localeResolver = null;

        try {
            localeResolver = (LocaleResolver) applicationContext.getBean(LocaleResolver.class);
        } catch (Exception e) {
            Assert.fail("Verify that you have configured CookieLocaleResolver in Spring ApplicationContext. Error: " + e.toString());
        }

        Assert.assertTrue("Verify that you have configured CookieLocaleResolver in Spring ApplicationContext.", localeResolver.getClass().equals(CookieLocaleResolver.class));
    }
}