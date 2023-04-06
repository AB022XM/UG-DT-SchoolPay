import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'biller',
        data: { pageTitle: 'schoolPayApp.biller.home.title' },
        loadChildren: () => import('./biller/biller.module').then(m => m.BillerModule),
      },
      {
        path: 'student',
        data: { pageTitle: 'schoolPayApp.student.home.title' },
        loadChildren: () => import('./student/student.module').then(m => m.StudentModule),
      },
      {
        path: 'customer',
        data: { pageTitle: 'schoolPayApp.customer.home.title' },
        loadChildren: () => import('./customer/customer.module').then(m => m.CustomerModule),
      },
      {
        path: 'biller-catergory',
        data: { pageTitle: 'schoolPayApp.billerCatergory.home.title' },
        loadChildren: () => import('./biller-catergory/biller-catergory.module').then(m => m.BillerCatergoryModule),
      },
      {
        path: 'contact-info',
        data: { pageTitle: 'schoolPayApp.contactInfo.home.title' },
        loadChildren: () => import('./contact-info/contact-info.module').then(m => m.ContactInfoModule),
      },
      {
        path: 'address',
        data: { pageTitle: 'schoolPayApp.address.home.title' },
        loadChildren: () => import('./address/address.module').then(m => m.AddressModule),
      },
      {
        path: 'school',
        data: { pageTitle: 'schoolPayApp.school.home.title' },
        loadChildren: () => import('./school/school.module').then(m => m.SchoolModule),
      },
      {
        path: 'payment-ent',
        data: { pageTitle: 'schoolPayApp.paymentEnt.home.title' },
        loadChildren: () => import('./payment-ent/payment-ent.module').then(m => m.PaymentEntModule),
      },
      {
        path: 'validate-customer-by-id',
        data: { pageTitle: 'schoolPayApp.validateCustomerById.home.title' },
        loadChildren: () => import('./validate-customer-by-id/validate-customer-by-id.module').then(m => m.ValidateCustomerByIdModule),
      },
      {
        path: 'paybill',
        data: { pageTitle: 'schoolPayApp.paybill.home.title' },
        loadChildren: () => import('./paybill/paybill.module').then(m => m.PaybillModule),
      },
      {
        path: 'associated-fees',
        data: { pageTitle: 'schoolPayApp.associatedFees.home.title' },
        loadChildren: () => import('./associated-fees/associated-fees.module').then(m => m.AssociatedFeesModule),
      },
      {
        path: 'payment-channel-ent',
        data: { pageTitle: 'schoolPayApp.paymentChannelEnt.home.title' },
        loadChildren: () => import('./payment-channel-ent/payment-channel-ent.module').then(m => m.PaymentChannelEntModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
