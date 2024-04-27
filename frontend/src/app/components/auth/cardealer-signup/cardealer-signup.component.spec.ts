import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardealerSignupComponent } from './cardealer-signup.component';

describe('CardealerSignupComponent', () => {
  let component: CardealerSignupComponent;
  let fixture: ComponentFixture<CardealerSignupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CardealerSignupComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CardealerSignupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
