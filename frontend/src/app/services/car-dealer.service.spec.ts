import { TestBed } from '@angular/core/testing';

import { CarDealerService } from './car-dealer.service';

describe('CarDealerService', () => {
  let service: CarDealerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CarDealerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
