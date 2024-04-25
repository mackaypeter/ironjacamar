/*
 * IronJacamar, a Java EE Connector Architecture implementation
 * Copyright 2009, Red Hat Inc, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.jca.core.workmanager.spec.chapter10.common;

import java.util.concurrent.CountDownLatch;

import jakarta.resource.spi.work.Work;

/**
 * LongRunningWork
 * @version $Revision: $
 */
public class LongRunningWork implements Work
{
   private boolean wasReleased;
   private long releaseThread;
   private boolean postRun;
   private CountDownLatch start;
   private CountDownLatch done;
   private long threadId;
   
   /**
    * Constructor.
    */
   public LongRunningWork()
   {
   }
   
   /**
    * Constructor.
    * @param start Latch when enter run method
    * @param done Latch when leave run method
    */
   public LongRunningWork(CountDownLatch start, CountDownLatch done) 
   {
      this.start = start;
      this.done = done;
   }
   
   /**
    * release method
    */
   public void release()
   {
      wasReleased = true;
      releaseThread = Thread.currentThread().getId();
   }

   /**
    * run method
    */
   public void run()
   {
      System.out.println("Ima running!");
      try
      {
         start.await();
      } 
      catch (InterruptedException e)
      {
         e.printStackTrace();
      }
      threadId = Thread.currentThread().getId();
      System.out.println("I completed!");
      postRun = true;
      System.out.println("I postrunned!");
      System.out.println("posTrun: " + this.postRun);
      System.out.println("posTrun: " + this.hasPostRun());
      done.countDown(); 
   }
   
   /**
    * run method
    * @return boolean wasReleased
    */
   public boolean getWasReleased()
   {
      return wasReleased;
   }
   
   /**
    * get releasing thread
    * @return long thread
    */
   public long getReleaseThread()
   {
      return releaseThread;
   }

   /**
    * @return long current thread id
    */
   public long getThreadId()
   {
      return threadId;
   }
   
   /**
    * @return boolean if enter run method, has executed
    */
   public boolean hasPostRun()
   {
      return postRun;
   }
}

