import { TestBed } from '@angular/core/testing';

import { DashViewApiService } from './dash-view-api.service';

describe('DashViewApiService', () => {
  let service: DashViewApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DashViewApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
