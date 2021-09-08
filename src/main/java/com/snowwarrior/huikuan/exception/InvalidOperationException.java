package com.snowwarrior.huikuan.exception;

import com.snowwarrior.huikuan.constant.ErrorConstants;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class InvalidOperationException extends AbstractThrowableProblem {
    public InvalidOperationException(String message) {
        super(ErrorConstants.DEFAULT_TYPE, message, Status.CONFLICT);
    }
}
