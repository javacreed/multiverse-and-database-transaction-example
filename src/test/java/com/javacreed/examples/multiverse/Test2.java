package com.javacreed.examples.multiverse;

import org.junit.Assert;
import org.junit.Test;

public class Test2 extends AbstractTest {

  @Test
  public void test() {
    final Account a = load(1);
    Assert.assertEquals(10, a.getBalance());

    final Account b = load(2);
    Assert.assertEquals(5, b.getBalance());

    try {
      teller.transfer(b, a, 10);
      Assert.fail("Should throw InsufficientFundsException");
    } catch (final InsufficientFundsException e) {// Expected
    }

    Assert.assertEquals(10, a.getBalance());
    Assert.assertEquals(10, getBalance(1));

    Assert.assertEquals(5, b.getBalance());
    Assert.assertEquals(5, getBalance(2));
  }

}
