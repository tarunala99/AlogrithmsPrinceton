import java.util.concurrent.Semaphore;

class Foo {
    
    Semaphore sem;
    
    public Foo() {
        
    }

    public void first(Runnable printFirst) throws InterruptedException {
    	sem.acquire();
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        sem.release();
        
    }

    public void second(Runnable printSecond) throws InterruptedException {
    	sem.acquire();
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        sem.release();
    }

    public void third(Runnable printThird) throws InterruptedException {
    	sem.acquire();
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
        sem.release();
    }
}