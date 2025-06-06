package com.capstone2.capstone2.domain.groupPurchase.handler;

import com.capstone2.capstone2.global.error.code.status.BaseErrorCode;
import com.capstone2.capstone2.global.exception.GeneralException;

public class ParticipationHandler extends GeneralException {
  public ParticipationHandler(BaseErrorCode baseErrorCode) {
    super(baseErrorCode);
  }
}