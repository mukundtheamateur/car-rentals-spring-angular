import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MycarsComponent } from './mycars.component';

describe('MycarsComponent', () => {
  let component: MycarsComponent;
  let fixture: ComponentFixture<MycarsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MycarsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MycarsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
