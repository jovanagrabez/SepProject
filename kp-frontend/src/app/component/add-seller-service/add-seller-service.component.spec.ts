import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddSellerServiceComponent } from './add-seller-service.component';

describe('AddPaymentServiceComponent', () => {
  let component: AddSellerServiceComponent;
  let fixture: ComponentFixture<AddSellerServiceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddSellerServiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddSellerServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
