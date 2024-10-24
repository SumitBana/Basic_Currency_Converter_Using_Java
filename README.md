# Currency_Converter_Using_Java

This Java program performs currency conversion using the Open Exchange Rates API. It allows users to convert an amount from a base currency (USD by default) to a target currency of their choice.

## Prerequisites
- Java installed on your machine.
- An active API key from Open Exchange Rates.

## Usage
### Input Requirements
- The program currently accepts USD * as the base currency.
- Choose the target currency code (e.g., EUR, GBP, etc.).
- Input the amount to convert.

### API Key
- To use the program, an API key from Open Exchange Rates is required.
- Replace the placeholder apiKey variable in the code with your API key in Line 36 (if any).
- Run the Program

### Execute the program.
- Enter the required inputs as prompted.

## Notice
### Usage with Purchased API Key

### Changing Base Currency
- By utilizing a purchased API key from Open Exchange Rates, users gain the flexibility to set the base currency as per their requirements.
- In src/CurrencyConverter.java , cmbbasecurr.disable(); (Line 287) can be changed if API Key is Purchased to get the full experience.
  
### Enhanced Customization
- With a purchased API key, users can adapt the program to work with different base currencies according to their subscription plan on Open Exchange Rates.
