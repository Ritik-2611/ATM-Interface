    import java.util.InputMismatchException;
    import java.util.Scanner;
    import java.util.Random;

    class Customer{
        Random ran=new Random();
        int id;
        int i=0,j=0;
        int transaction[]=new int[15];
        int indicator[]=new int[15];
        int crnt_bal=0;
        String cst_name;
        int pin;
        public void Money(int amnt,int indicator){
            if (indicator == 1) {
                crnt_bal+=amnt;
            } else {
                crnt_bal-=amnt;
            }
            transaction[i++]=amnt;
            this.indicator[j++]=indicator;
        }
        public Customer(String cust_name,int pin){
            this.cst_name=cust_name;
            this.pin=pin;
            id= ran.nextInt(6,28);
            crnt_bal=0;
        }
    }

    public class ATM {
        Scanner sc = new Scanner(System.in);
        Customer[][] customer_det = new Customer[3][20];
        int[] customer_cnt = new int[3];
        int select_bank;

        public void bank() {
            while(true) {
                System.out.println("---------------------------------------------------------------------");
                System.out.println("<----------WELCOME TO ATM SERVICES------------->");
                System.out.println("ENTER ATM SERVICES : ");
                System.out.println("1 --> SBI BANK\n2 -->J&K BANK\n3 --> PNB\n");
                select_bank = sc.nextInt() - 1;
                System.out.println("---------------------------------------------------------------------");
                if (select_bank >= 0 & select_bank < 3) {
                    System.out.println("<------------------Welcome ... To " + BankName(select_bank) + "---------------------->");
                    Operations();
                } else
                    System.out.println(BankName(select_bank) + "\n!!!!!!!TRY AGAIN!!!!!!");
            }
        }

        public void Operations() {
            int choice;
            System.out.println("ENTER : ");
            System.out.println("1 --> NEW USER\n2 --> ALREADY HAVE ACCOUNT\n3 -->EXIT");
            System.out.println("---------------------------------------------------------------------");
            choice = sc.nextInt();
            if (choice == 1)
                add(select_bank);
            else if (choice == 2)
                authentication(select_bank);
            else if (choice == 3) bank();
            else {
                System.out.println(" DHAKKAN BS");
            }
        }

        public void profile(int bnk_id, int cst_id) {
            Customer c = customer_det[bnk_id][cst_id];
            System.out.println("---------------------------------------------------------------------");
            System.out.println("Account Id --> " + c.id + "\nAccount Holder --> " + c.cst_name + "\nTotal Balance --> " + c.crnt_bal);
            System.out.println("---------------------------------------------------------------------\n");

        }

        public void history(int bnk_id, int cst_id) {
            Customer c=customer_det[bnk_id][cst_id];
            System.out.println("---------------------------------------------------------------------");
            for (int i = 0; i < c.transaction.length; i++) {
                if (c.indicator[i] == 1)
                    System.out.println("Deposited --> +" + c.transaction[i]);
                else if(c.indicator[i]==2)
                    System.out.println("Withdrawled --> -" + c.transaction[i]);

            }

            System.out.println("---------------------------------------------------------------------\n");

        }

        public void change_pwd(int bnk_id, int cst_id) {
            int old_pin=0,cnt=0;
            System.out.println("---------------------------------------------------------------------");
            while(old_pin != customer_det[bnk_id][cst_id].pin){
                System.out.println("ENTER CURRENT PIN : ");
                old_pin = sc.nextInt();
                if (old_pin != customer_det[bnk_id][cst_id].pin) {
                    System.out.println("YOU ARE FRAUD - - - >");
                    cnt++;
                    System.out.println("---------------------------------------------------------------------\n");
                }
                if(cnt>3){
                    System.out.println("<------------MAXIMUM ATTEMPT REACHED .. WE ARE LOGGING YOU OUT..------------->");
                    System.out.println("---------------------------------------------------------------------\n");
                    add(bnk_id);
                }
            }
            if (old_pin == customer_det[bnk_id][cst_id].pin) {
                System.out.println("ENTER NEW PIN : ");
                customer_det[bnk_id][cst_id].pin = sc.nextInt();
                System.out.println("<------------PASSWORD CHANGE SUCCESSFUL------------->");
            }
            System.out.println("---------------------------------------------------------------------\n");
        }

        public void chk_balance(int bnk_id, int cst_id) {
            System.out.println("--------------------------------------------------------------------");
            System.out.println("CURRENT BALANCE : " + customer_det[bnk_id][cst_id].crnt_bal);
            System.out.println("---------------------------------------------------------------------\n");
        }

        public void deposit_money(int bnk_id, int cst_id) {
            int amnt;

            System.out.println("----------------------------------------------------------------------");
            System.out.println("ENTER AMOUNT TO DEPOSIT");
            amnt = sc.nextInt();
            customer_det[bnk_id][cst_id].Money(amnt, 1);
            System.out.println("<------------DEPOSITION OF " + amnt + " IS SUCCESSFUL------------->");
            System.out.println("---------------------------------------------------------------------\n");

        }

        public void withdraw_cash(int bnk_id, int cst_id) {
            int amnt;
            System.out.println("----------------------------------------------------------------------");
            System.out.println("ENTER AMOUNT TO WITHDRAW");
            amnt = sc.nextInt();
            if (amnt > customer_det[bnk_id][cst_id].crnt_bal) {
                System.out.println("INSUFFICIENT BALANCE TO MAKE WITHDRAWL ... !!!");
                System.out.println("<------------WITHDRAWL FAILED------------->");
                System.out.println("---------------------------------------------------------------------");
                return;
            } else
                customer_det[bnk_id][cst_id].Money(amnt, 2);
            System.out.println("<------------WITHDRAWL OF " + amnt + " IS SUCCESSFUL------------->");

            System.out.println("---------------------------------------------------------------------\n");

        }

        public void cst_operation(int bnk_id, int i) {
            int choice = 0;
            System.out.println("---------------------------------------------------------------------");
            while (choice < 8) {
                System.out.println("WHAT WOULD YOU LIKE TO DO.. \nPRESS : ");
                System.out.println("1 --> CHECK BALANCE\n2 --> WITHDRAW CASH\n3 --> DEPOSIT MONEY\n4 --> HISTORY\n5 --> CHANGE PASSWORD\n6 --> SEE YOUR PROFILE\n7 -->EXIT");
                choice = sc.nextInt();
                if (choice == 1) chk_balance(bnk_id, i);
                else if (choice == 2) withdraw_cash(bnk_id, i);
                else if (choice == 3) deposit_money(bnk_id, i);
                else if (choice == 4) history(bnk_id, i);
                else if (choice == 5) change_pwd(bnk_id, i);
                else if (choice == 6) profile(bnk_id, i);
                else if (choice == 7) Operations();
                else {
                    System.out.println(" DHAKKAN BS");
                }
            }
            System.out.println("---------------------------------------------------------------------\n");
            bank();

        }

        public void authentication(int bnk_id) {
            String name;
            int pin, i, fond = 0;
            System.out.println("---------------------------------------------------------------------");
            System.out.println("<----------------ENTER THE BELOW DETAILS TO LOG IN -------------> ");
            sc.nextLine();
            System.out.print("ENTER YOUR NAME : ");
            name = sc.nextLine();
            System.out.print("ENTER PIN : ");
            pin = sc.nextInt();
            for (i = 0; i < customer_cnt[bnk_id]; i++) {
                if (customer_det[bnk_id][i].cst_name.equals(name) & customer_det[bnk_id][i].pin == pin) {
                    fond = 1;
                    break;
                }
            }
            if (fond == 1) {
                System.out.println("<- - - - - -WELCOME " + name + ", WE ARE DELIGHTED BY YOUR COMPANY- - - - - ->");
                System.out.println("---------------------------------------------------------------------\n");
                cst_operation(bnk_id, i);
            } else {
                int chk;
                System.out.println("INVALID USER..");
                System.out.println("ENTER : ");
                System.out.println("1 --> TRY AGAIN\n2 -->EXIT");
                chk = sc.nextInt();
                if (chk == 1)         {
                    System.out.println("---------------------------------------------------------------------\n");
                    authentication(bnk_id);}
                else if (chk == 2) {
                   System.out.println("---------------------------------------------------------------------\n");
                    Operations();}
                else {
                    System.out.println(" DHAKKAN BS");
                    System.out.println("---------------------------------------------------------------------\n");
                    Operations();
                }
            }


        }

        public void add(int bnk_id) {
            int choice;
            System.out.println("---------------------------------------------------------------------");
            if (customer_cnt[bnk_id] >= 20) {
                System.out.println(BankName(bnk_id) + "HAS REACHED ITS LIMIT..");
                System.out.println("---------------------------------------------------------------------\n");
                return;
            }
            String name;
            int pin=10000;

            sc.nextLine();
            System.out.print("ENTER NAME : ");
            name = sc.nextLine();
            while(((int) Math.log10(pin)+1)>4){
                System.out.println("ENTERED NAME : "+name);
                System.out.print("ENTER PIN (length = 4 ) : ");
                try {
                    pin = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("ONLY NUMBERS ARE ALLOWED");
                    System.out.println("---------------------------------------------------------------------\n");
                    sc.nextLine();
                }
                if(((int) Math.log10(pin)+1)!=4) System.out.println("YOUR PIN MUST BE 4 in LENGTH..");
                System.out.println("---------------------------------------------------------------------\n");
            }
            customer_det[bnk_id][customer_cnt[bnk_id]++] = new Customer(name, pin);

            System.out.println("YOUR DETAILS ARE ADDED...\n<--------WELCOME TO FAMILY OF " + BankName(bnk_id) + "-------->");
            System.out.println("---------------------------------------------------------------------");
            System.out.println("TO ACCESS ACCOUNT --> PRESS 1.");
            System.out.println("TO GO BACK --> PRESS 2.");
            System.out.println("---------------------------------------------------------------------");
            choice = sc.nextInt();
            if (choice == 1)         {
                authentication(bnk_id);}
            else if (choice == 2) {
                System.out.println("---------------------------------------------------------------------\n");
                Operations();}
            else {
                System.out.println(" DHAKKAN BS");
                System.out.println("---------------------------------------------------------------------\n");
                Operations();
            }


        }

        public String BankName(int select_bank) {
            if (select_bank == 0) return "SBI BANK";
            if (select_bank == 1) return "J&K BANK";
            if (select_bank == 2) return "PNB";
            return "INVALID CHOICE.\n YOLOYLYOYLYOYLYOYLYHO";
        }


        public static void main(String[] args) {
            ATM atm = new ATM();
            atm.bank();
        }
    }
