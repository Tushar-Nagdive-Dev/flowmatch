import { TestBed } from '@angular/core/testing';

import { DashViewService } from './dash-view.service';

describe('DashViewService', () => {
  let service: DashViewService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DashViewService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
