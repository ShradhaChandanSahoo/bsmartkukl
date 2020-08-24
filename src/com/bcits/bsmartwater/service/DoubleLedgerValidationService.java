package com.bcits.bsmartwater.service;

import com.bcits.bsmartwater.model.DoubleLedgerValidation;

public interface DoubleLedgerValidationService {
  void save(DoubleLedgerValidation doubleLedgerValidation);
  void update(DoubleLedgerValidation doubleLedgerValidation);
DoubleLedgerValidation getRecordByWardAndRdngDay(String wardNo, int parseInt);
}
