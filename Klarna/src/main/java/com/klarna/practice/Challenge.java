package com.klarna.practice;

class Challenge {
    // Complete this function to return either
    // "Hello, [name]!" or "Hello there!"
    // based on the input
    
    public static String sayHello(String name) throws ValidationException {
        if(name == null || name.isEmpty()) {
          throw new ValidationException("Invalid Input passed");
        }
        
        boolean allLetters = name.chars().allMatch(Character::isLetter);
        
        if(!allLetters){
          throw new ValidationException("Input format exception");
        }
        
        // but you need to return the correct value in order to pass the challenge
        return  String.format("Hello, %s!", name); 
    }
}

class ValidationException extends Exception{
  public ValidationException(String details){
    super(details);
  }
}
