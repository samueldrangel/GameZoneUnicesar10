1. **Question:** The return is a new entity in the system that references an existing sale. What type of relationship exists between the Return class and the Sale class? Is this relationship of inheritance, association, aggregation, or composition? Justify[cite: 8].
   **Answer:** The relationship between Return and Sale is an association because a Return references an existing Sale object without managing its lifecycle[cite: 8, 9].

2. **Question:** A return can contain only some products from the original sale, not necessarily all of them. How is this situation represented in the attributes of the Return class? What is stored in the returned products attribute?[cite: 8]
   **Answer:** This situation is represented using a List<Product> inside the Return class containing specifically the returned items[cite: 8, 9].

3. **Question:** The business rule states that returns can only be registered within 30 days after the sale. In which layer of the system is this validation located and why? What Java mechanism is used to calculate the difference between two dates?[cite: 8]
   **Answer:** This validation is located in the service layer (ReturnService) because business rules must be validated before persistence. Java uses java.time.LocalDate and ChronoUnit.DAYS.between() to compute date differences[cite: 8, 9].

4. **Question:** Returning products increases stock. What existing method from the Workshop 1 system is reused for this operation, and in which class is it invoked from the returns module? Why is it important to reuse existing methods instead of duplicating stock update logic?[cite: 8]
   **Answer:** The restoreStock method from ProductService is reused, and it is invoked from ReturnService to avoid code duplication and maintain stock consistency[cite: 8, 9].

5. **Question:** The monthly balance report requires consolidating information from two different modules (sales and returns). In which service class is this report located and why is this location coherent with the layered architecture? What dependencies does this class need to generate it?[cite: 9]
   **Answer:** This report is placed in ReturnService because it consolidates data from both sales and returns repositories, and it needs ReturnRepository, SaleService, and ProductService as dependencies[cite: 9, 10].