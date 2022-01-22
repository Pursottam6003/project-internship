//selecting the items 
// My Simple Calculator 
//selecting the  const items 
let screen = document.getElementById('screen');
buttons = document.querySelectorAll('button');
let mycalculator = document.getElementById('contain');
let starting = document.querySelector('.first');

let screenValue = ''; //emptying the screen value 
let btn = document.getElementById('start');
//calling the function to click first 
btn.addEventListener('click',()=> {
     
    mycalculator.style.opacity=1;
    mycalculator.style.transition="0.4s"
})

// loop  and inside the funtion it is used to display and get the result from the input
for (item of buttons) {
    //items defined under buttons
    //calling the function 
    item.addEventListener('click', (e) => {
        buttonText = e.target.innerText;
        console.log('Button text is ', buttonText);
        if (buttonText == 'X') {
            console.log('hello')
            buttonText = '*';         
            screenValue += buttonText;
            screen.value = screenValue; 
        }

        else if (buttonText =='ln')
        {
            let result=0;
            buttonText=''; //emptying the screen
            result+=Math.log(screenValue);
            screen.value='';//emptying the screen
            screen.value=result; // assigning the result and showing in the screen 
        }
        else if (buttonText =='Sqrt')
        {
            
            console.log(screenValue);
            let square=0;
            buttonText='';//emptying the value 
            square +=Math.sqrt(screenValue);
            screen.value =' ';
            screen.value=square;
        }
        //emptying the value 
        else if (buttonText == 'C') {
            screenValue = "";//emptying the value 
            screen.value = screenValue;
        }
        else if (buttonText == '=') {
            screen.value = eval(screenValue);
        }
        // when we are just writing the input numbers it will display them
        else {
            screenValue += buttonText;
            screen.value = screenValue;
        }

    })
}

