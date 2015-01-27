package com.jason;

public class PushExecutorTest {
    public static void main(String[] args) {
        QueueService queueService = createQueueService();
        ThreadPoolPushExecutor threadPoolPushExecutor = new ThreadPoolPushExecutor(3, new PushService(), queueService);
        new Thread(new UpdateThreadCount(threadPoolPushExecutor)).start();
        new Thread(new UpdateThreadCount2(threadPoolPushExecutor)).start();
    }

    private static class UpdateThreadCount extends Thread {
        private ThreadPoolPushExecutor threadPoolPushExecutor;

        public UpdateThreadCount(ThreadPoolPushExecutor threadPoolPushExecutor) {
            this.threadPoolPushExecutor = threadPoolPushExecutor;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            updateThread(2);
            super.run();
        }

        private void updateThread(int threadCount) {
            threadPoolPushExecutor.updateThreadCount(threadCount);
        }
    }

    private static class UpdateThreadCount2 extends Thread {
        private ThreadPoolPushExecutor threadPoolPushExecutor;

        public UpdateThreadCount2(ThreadPoolPushExecutor threadPoolPushExecutor) {
            this.threadPoolPushExecutor = threadPoolPushExecutor;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            updateThread(4);
            super.run();
        }

        private void updateThread(int threadCount) {
            threadPoolPushExecutor.updateThreadCount(threadCount);
        }
    }

    private static class UpdateTask extends Thread {
        private QueueService queueService;

        public UpdateTask(QueueService queueService) {
            this.queueService = queueService;
        }

        @Override
        public void run() {
            addTask();
            super.run();
        }

        public void addTask() {
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            queueService.addTask(createHotelChange("Hotel-21", null, LocalDateTimes.of("2015-02-01 10:21:10").toDate().getTime()));
            queueService.addTask(createHotelChange("Hotel-22", null, LocalDateTimes.of("2015-02-01 10:21:25").toDate().getTime()));
        }
    }

    private static QueueService createQueueService() {
        QueueService queueService = new QueueService();
        queueService.addTask(createHotelChange("Hotel-01", null, LocalDateTimes.of("2015-02-01 08:21:22").toDate().getTime()));
        queueService.addTask(createHotelChange("Hotel-02", null, LocalDateTimes.of("2015-02-01 08:21:22").toDate().getTime()));
        queueService.addTask(createHotelChange("Hotel-03", null, LocalDateTimes.of("2015-02-01 09:21:22").toDate().getTime()));
        queueService.addTask(createHotelChange("Hotel-04-1", Status.CANCELLED, LocalDateTimes.of("2015-02-01 08:21:22").toDate().getTime()));
        queueService.addTask(createHotelChange("Hotel-04-2", Status.CANCELLED, LocalDateTimes.of("2015-02-01 08:21:25").toDate().getTime()));
        queueService.addTask(createHotelChange("Hotel-05", null, LocalDateTimes.of("2015-02-01 08:21:20").toDate().getTime()));
        queueService.addTask(createHotelChange("Hotel-06-1", Status.RE_FETCH, LocalDateTimes.of("2015-02-01 08:21:20").toDate().getTime()));
        queueService.addTask(createHotelChange("Hotel-06-2", Status.RE_FETCH, LocalDateTimes.of("2015-02-01 08:21:22").toDate().getTime()));
        queueService.addTask(createHotelChange("Hotel-06-3", Status.RE_FETCH, LocalDateTimes.of("2015-02-01 08:21:02").toDate().getTime()));
        queueService.addTask(createHotelChange("Hotel-06-4", Status.RE_FETCH, LocalDateTimes.of("2015-02-01 08:21:01").toDate().getTime()));
        queueService.addTask(createHotelChange("Hotel-06-5", Status.RE_FETCH, LocalDateTimes.of("2015-02-01 08:21:03").toDate().getTime()));
        queueService.addTask(createHotelChange("Hotel-06-6", Status.RE_FETCH, LocalDateTimes.of("2015-02-01 08:21:02").toDate().getTime()));
        queueService.addTask(createHotelChange("Hotel-06-7", Status.RE_FETCH, LocalDateTimes.of("2015-02-01 08:20:02").toDate().getTime()));
        queueService.addTask(createHotelChange("Hotel-07", null, LocalDateTimes.of("2015-02-01 08:21:20").toDate().getTime()));
        queueService.addTask(createHotelChange("Hotel-08", null, LocalDateTimes.of("2015-02-01 08:21:05").toDate().getTime()));
        queueService.addTask(createHotelChange("Hotel-09", null, LocalDateTimes.of("2015-02-01 08:21:10").toDate().getTime()));
        queueService.addTask(createHotelChange("Hotel-10", null, LocalDateTimes.of("2015-02-01 08:21:25").toDate().getTime()));
        queueService.addTask(createHotelChange("Hotel-11", null, LocalDateTimes.of("2015-02-01 08:21:25").toDate().getTime()));
        queueService.addTask(createHotelChange("Hotel-12", null, LocalDateTimes.of("2015-02-01 08:21:25").toDate().getTime()));
        queueService.addTask(createHotelChange("Hotel-13", null, LocalDateTimes.of("2015-02-01 08:21:25").toDate().getTime()));
        queueService.addTask(createHotelChange("Hotel-14", null, LocalDateTimes.of("2015-02-01 08:21:25").toDate().getTime()));
        queueService.addTask(createHotelChange("Hotel-15", null, LocalDateTimes.of("2015-02-01 08:21:25").toDate().getTime()));
        for (int i = 0; i < 50; i++) {
            queueService.addTask(createHotelChange(String.format("Hotel-00%d", i), null, LocalDateTimes.of("2015-02-01 08:21:26").toDate().getTime()));
        }
        return queueService;
    }

    private static HotelChange createHotelChange(String hotelCode, Status status, long timeStamp) {
        HotelChange hotelChange = new HotelChange(hotelCode);
        if (status != null) {
            if (status == Status.CANCELLED) {
                hotelChange.cancel();
            }
            if (status == Status.RE_FETCH) {
                hotelChange.reFetch();
            }
        }
        hotelChange.setTimeStamp(timeStamp);
        return hotelChange;
    }
}
