# 🏦 SecureBank - Online Banking Portal

## How to Compile & Run

### Step 1 - Open Command Prompt, go to src folder
cd BankingApp\src

### Step 2 - Compile (copy paste this full command)
javac com\bank\model\Account.java com\bank\model\SavingsAccount.java com\bank\exceptions\InsufficientFundsException.java com\bank\db\Database.java com\bank\server\BankServer.java com\bank\ui\LoginFrame.java com\bank\ui\RegisterFrame.java com\bank\ui\ChangePasswordFrame.java com\bank\ui\StatementFrame.java com\bank\ui\AdminFrame.java com\bank\ui\DashboardFrame.java com\bank\Main.java

### Step 3 - Run
java com.bank.Main

### Step 4 - Open browser (localhost server)
http://localhost:8080

---

## Test Accounts
| Account No | Password  | Balance     |
|------------|-----------|-------------|
| ACC001     | rahul123  | Rs. 50,000  |
| ACC002     | priya123  | Rs. 30,000  |
| ACC003     | amit123   | Rs. 75,000  |

## Admin Login
| Username | Password  |
|----------|-----------|
| ADMIN    | admin123  |

Logs into a dashboard listing every customer account, holder name, balance, and interest rate.

---

## Features
- Maximized fullscreen UI with logo
- Login with Enter key + mouse hover effects
- Create New Account (Registration)
- Deposit / Withdraw / Transfer
- Change Password
- Mini Statement (color-coded table)
- Transaction log saved to transactions.txt
- Localhost server at http://localhost:8080
- Admin Dashboard (view all accounts and balances)

## Syllabus Coverage
| Topic              | Location                        |
|--------------------|---------------------------------|
| Classes            | Account.java                    |
| Inheritance        | SavingsAccount extends Account  |
| Packages           | com.bank.model/ui/db/server     |
| Exception Handling | InsufficientFundsException      |
| Threads            | runInThread() in DashboardFrame |
| File I/O           | Database.logTransaction()       |
| Mouse Events       | Hover on Login button           |
| Keyboard Events    | Enter key to login              |
| Networking         | BankServer (localhost:8080)     |
| JDBC Reference     | db/banking.sql                  |
