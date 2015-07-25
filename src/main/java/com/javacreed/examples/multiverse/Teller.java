package com.javacreed.examples.multiverse;

import org.multiverse.api.StmUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

public class Teller {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Transactional("tjtJTransactionManager")
  public void transfer(final Account from, final Account to, final int amount) throws InsufficientFundsException {
    StmUtils.atomic(new Runnable() {
      @Override
      public void run() {
        // Update the objects in memory
        from.adjustBy(-amount);
        to.adjustBy(amount);

        // Update the database
        jdbcTemplate.update("UPDATE `accounts` SET `balance` = ? WHERE `account_id` = ?",
            new Object[] { from.getBalance(), from.getId() });
        jdbcTemplate.update("UPDATE `accounts` SET `balance` = ? WHERE `account_id` = ?",
            new Object[] { to.getBalance(), to.getId() });

        // Validate after the db update on purpose (so we fail after all updates are made)
        if (from.getBalance() < 0 || to.getBalance() < 0) {
          throw new InsufficientFundsException();
        }
      }
    });
  }
}
