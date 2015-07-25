package com.javacreed.examples.multiverse;

import org.multiverse.api.StmUtils;
import org.multiverse.api.Txn;
import org.multiverse.api.callables.TxnCallable;
import org.multiverse.api.callables.TxnIntCallable;
import org.multiverse.api.references.TxnInteger;

public class Account {

  private final TxnInteger id;
  private final TxnInteger balance;

  public Account(final int id, final int balance) {
    this.id = StmUtils.newTxnInteger(id);
    this.balance = StmUtils.newTxnInteger(balance);
  }

  public void adjustBy(final int amount) {
    StmUtils.atomic(new Runnable() {
      @Override
      public void run() {
        balance.increment(amount);
      }
    });
  }

  public int getBalance() {
    return StmUtils.atomic(new TxnIntCallable() {
      @Override
      public int call(final Txn txn) throws Exception {
        return balance.get(txn);
      }
    });
  }

  public int getId() {
    return StmUtils.atomic(new TxnIntCallable() {
      @Override
      public int call(final Txn txn) throws Exception {
        return id.get(txn);
      }
    });
  }

  @Override
  public String toString() {
    return StmUtils.atomic(new TxnCallable<String>() {
      @Override
      public String call(final Txn txn) throws Exception {
        return String.format("Balance: %d", balance.get(txn));
      }
    });
  }

}
