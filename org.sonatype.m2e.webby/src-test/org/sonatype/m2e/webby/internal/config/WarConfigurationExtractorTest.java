package org.sonatype.m2e.webby.internal.config;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WarConfigurationExtractorTest {

  private WarConfigurationExtractor configExtractor;

  @Before
  public void before() {
    configExtractor = new WarConfigurationExtractor();
  }

  @Test
  public void testResolve() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    Assert.assertEquals("test/path", resolve(null, "test/path"));
    Assert.assertNull(resolve(null, null));
    Assert.assertEquals("/tmp/basedir/test/path", resolve("/tmp/basedir", "test/path"));
    Assert.assertEquals(getBasedir() + "/basedir/test/path", resolve("./basedir", "test/path"));
    Assert.assertEquals(getBasedir() + "/basedir/test/path", resolve("basedir", "test/path"));
    Assert.assertEquals(getBasedir() + "/basedir/test/path", resolve("basedir", "./test/path"));
    Assert.assertEquals("/test/path", resolve("basedir", "/test/path"));
    Assert.assertEquals("/test/path", resolve("basedir", "\\\\test\\path"));
    Assert.assertEquals("/test/path", resolve("/basedir", "\\\\test\\path"));
  }

  private String resolve(String basedir, String path) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Method method = WarConfigurationExtractor.class.getDeclaredMethod("resolve", String.class, String.class);
    method.setAccessible(true);
    return (String) method.invoke(configExtractor, basedir, path);
  }
  
  private static String getBasedir(){
    return System.getProperty("user.dir");
  }
}
