import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaymentServicesComponent } from './payment-services.component';

describe('PaymentServicesComponent', () => {
  let component: PaymentServicesComponent;
  let fixture: ComponentFixture<PaymentServicesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaymentServicesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaymentServicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
