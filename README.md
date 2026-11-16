# Shopping Cart Demo

A simple Java console project for the Software Design Patterns final.

## Team responsibilities

- Abilmansur: Builder, Strategy, Facade.
- Miras: Observer, Decorator, Adapter.

## Current progress

- A1: shared Product and DiscountStrategy interfaces.
- A2: configurable Laptop with a nested Builder.
- A3: ShoppingCart with NoDiscount and StudentDiscount (10%).
- A4: ShopFacade coordinates cart validation, payment and stock updates.
- PaymentProcessor is the shared payment contract; Participant B will implement PaymentAdapter.
- ProductInfo currently provides the shared data model; Participant B will add Observer notifications.

## Requirements

JDK 17 or 21. Open the project in IntelliJ IDEA and build it using Build Project.

All Java files are in src without package declarations. The console entry point will be added in a later step.
