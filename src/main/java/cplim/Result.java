package cplim;

public class Result {
    private boolean success;
    private String message;
    private GameStatistics statistic;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatistic(GameStatistics statistic) {
        this.statistic = statistic;
    }

    public GameStatistics getStatistic() {
        return statistic;
    }
}
