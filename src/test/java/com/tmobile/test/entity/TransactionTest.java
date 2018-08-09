/******************************************************************************
 *
 * Copyright (c) 2018 CA.  All rights reserved.
 *
 * This software and all information contained therein is confidential and
 * proprietary and shall not be duplicated, used, disclosed or disseminated
 * in any way except as authorized by the applicable license agreement,
 * without the express written permission of CA. All authorized reproductions
 * must be marked with this language.
 *
 * EXCEPT AS SET FORTH IN THE APPLICABLE LICENSE AGREEMENT, TO THE EXTENT
 * PERMITTED BY APPLICABLE LAW, CA PROVIDES THIS SOFTWARE WITHOUT
 * WARRANTY OF ANY KIND, INCLUDING WITHOUT LIMITATION, ANY IMPLIED
 * WARRANTIES OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  IN
 * NO EVENT WILL CA BE LIABLE TO THE END USER OR ANY THIRD PARTY FOR ANY
 * LOSS OR DAMAGE, DIRECT OR INDIRECT, FROM THE USE OF THIS SOFTWARE,
 * INCLUDING WITHOUT LIMITATION, LOST PROFITS, BUSINESS INTERRUPTION,
 * GOODWILL, OR LOST DATA, EVEN IF CA IS EXPRESSLY ADVISED OF SUCH LOSS OR
 * DAMAGE.
 *
 ******************************************************************************/

package com.tmobile.test.entity;

import static com.tmobile.test.entity.Transaction.DATE_FORMAT;
import static org.junit.Assert.assertEquals;

import java.time.format.DateTimeFormatter;
import org.junit.Test;

/**
 * File description
 *
 * @author jakro01
 */
public class TransactionTest {
	@Test
	public void testCreateTxn() {
		String line = "payment weekly, Netflix/603603603, 2013-09-05 14:08:15";
		Transaction transaction = Transaction.createTransactionFromString(line);

		assertEquals("payment weekly", transaction.getDescription());
		assertEquals("Netflix", transaction.getPartner().getCompanyName());
		assertEquals("603603603", transaction.getPartner().getPhoneNumber());
		assertEquals("2013-09-05 14:08:15",
				transaction.getTimestamp().format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTxnEmptyLine() {
		Transaction.createTransactionFromString("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTxnWrongFormat1() {
		String line = "payment weekly; Netflix/603603603, 2013-09-05 14:08:15";
		Transaction.createTransactionFromString(line);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTxnWrongFormat2() {
		String line = "payment, weekly, Netflix/603603603, 2013-09-05 14:08:15";
		Transaction.createTransactionFromString(line);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTxnWrongDateFormat() {
		String line = "payment weekly; Netflix/603603603, 2013-09-05";
		Transaction.createTransactionFromString(line);
	}

	@Test
	public void testCreateTxnWithSpecialChars() {
		String line = "\"O2TV, SportTV\", \"Super Name, and additional / text\" /603605506, 2016-01-02 15:15:01";
		Transaction transaction = Transaction.createTransactionFromString(line);
		assertEquals("O2TV, SportTV", transaction.getDescription());
		assertEquals("Super Name, and additional / text", transaction.getPartner().getCompanyName());
		assertEquals("603605506", transaction.getPartner().getPhoneNumber());
		assertEquals("2016-01-02 15:15:01",
				transaction.getTimestamp().format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
	}

	@Test
	public void testCreateTxnTrimString() {
		String line = "   	 payment       weekly, Netflix	/	603603603, 2013-09-05 		 14:08:15		   	";
		Transaction transaction = Transaction.createTransactionFromString(line);

		assertEquals("payment weekly", transaction.getDescription());
		assertEquals("Netflix", transaction.getPartner().getCompanyName());
		assertEquals("603603603", transaction.getPartner().getPhoneNumber());
		assertEquals("2013-09-05 14:08:15",
				transaction.getTimestamp().format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
	}

	@Test
	public void testCreateTxnWithSpacesInQuotes() {
		String line = "   	\"	   O2TV	, 		SportTV   \"				,   \"   Super      Name, and 			additional / text\" /603605506, 2016-01-02 15:15:01";
		Transaction transaction = Transaction.createTransactionFromString(line);
		assertEquals("	   O2TV	, 		SportTV   ", transaction.getDescription());
		assertEquals("   Super      Name, and 			additional / text", transaction.getPartner().getCompanyName());
		assertEquals("603605506", transaction.getPartner().getPhoneNumber());
		assertEquals("2016-01-02 15:15:01",
				transaction.getTimestamp().format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
	}
}
