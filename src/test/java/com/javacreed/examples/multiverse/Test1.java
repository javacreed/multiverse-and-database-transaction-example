package com.javacreed.examples.multiverse;

import org.junit.Assert;

public class Test1 extends AbstractTest {

  @org.junit.Test
  public void test() {
    final Account a = load(1);
    Assert.assertEquals(10, a.getBalance());

    final Account b = load(2);
    Assert.assertEquals(5, b.getBalance());

    teller.transfer(a, b, 5);

    Assert.assertEquals(5, a.getBalance());
    Assert.assertEquals(5, getBalance(1));

    Assert.assertEquals(10, b.getBalance());
    Assert.assertEquals(10, getBalance(2));
  }

}
