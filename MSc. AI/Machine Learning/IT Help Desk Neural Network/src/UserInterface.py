import numpy as np
# unused
class uiTool:
    def __init__(self):
        super().__init__()
        print("Welcome to the IT help-desk.")
        print("Please answer the questions below")
    
    def askUser(self):
        request = input("Is this a request? (Y/N)")
        incident = input("Is this an incident? (Y/N)")
        webServices = input("Is this a Web Services issue? (Y/N)")
        login = input("Is this a login issue? (Y/N)")
        wireless = input("Is this a wireless issue? (Y/N)")
        printing = input("Is this a printing issue? (Y/N)")
        idCards = input("Is this an ID card issue? (Y/N)")
        staff = input("Is this a staff issue? (Y/N)")
        students = input("Is this a student issue? (Y/N)")
        userInput = np.array([request, incident, webServices, login, wireless, printing, idCards, staff, students])
        encondedInput = np.array([])
        for ticket in userInput :
            if ticket == "Y":
                ticket = 1
            elif ticket == "N":
                ticket = 0
            encondedInput = np.append(encondedInput, ticket)
        print(encondedInput)
        return encondedInput
        
