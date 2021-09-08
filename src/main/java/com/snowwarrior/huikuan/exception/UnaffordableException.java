package com.snowwarrior.huikuan.exception;

import com.snowwarrior.huikuan.constant.ErrorConstants;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class UnaffordableException extends AbstractThrowableProblem {
    public UnaffordableException(String message) {
        super(ErrorConstants.DEFAULT_TYPE, message, Status.CONFLICT);
    }
}
