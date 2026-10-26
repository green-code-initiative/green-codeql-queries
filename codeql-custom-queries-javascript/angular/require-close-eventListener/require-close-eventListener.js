// Bad: addEventListener without removeEventListener
const button = document.getElementById('btn');
button.addEventListener('click', function() {
  console.log('Button clicked');
});

// Bad: addEventListener in a component that gets destroyed
class MyComponent {
  constructor() {
    this.element = document.querySelector('.container');
    // Event listener added but never removed
    this.element.addEventListener('mouseover', () => {
      console.log('Mouse over');
    });
  }
}

// Bad: addEventListener in a loop without removal
const buttons = document.querySelectorAll('button');
buttons.forEach((btn) => {
  btn.addEventListener('click', handleClick);
  // Missing: btn.removeEventListener('click', handleClick);
});

function handleClick(e) {
  console.log('Clicked');
}

// Good: addEventListener with matching removeEventListener
const element = document.getElementById('myElement');
const clickHandler = () => {
  console.log('Clicked');
};
element.addEventListener('click', clickHandler);

// Cleanup
element.removeEventListener('click', clickHandler);

// Good: addEventListener in component with cleanup
class GoodComponent {
  constructor() {
    this.element = document.querySelector('.item');
    this.handler = this.handleEvent.bind(this);
    this.element.addEventListener('click', this.handler);
  }

  destroy() {
    this.element.removeEventListener('click', this.handler);
  }

  handleEvent(e) {
    console.log('Event handled');
  }
}


// bad: addEventListener with no matching removeEventListener
const element2 = document.getElementById('myElement');
const clickHandler2 = () => {
  console.log('Clicked');
};
element2.addEventListener('click', clickHandler2);

// Cleanup
element2.removeEventListener('mouseover', clickHandler2);


// bad: addEventListener with no matching removeEventListener
const element3 = document.getElementById('myElement');
const clickHandler3 = () => {
  console.log('Clicked');
};
element3.addEventListener('click', clickHandler3);

// Cleanup
element3.removeEventListener('mouseover', clickHandler2);

// Good
function attachAndRemoveEventListener() {
  let element = document.getElementById("btn");
  function handleClick() {
    console.log("Button clicked");
  }
  element.addEventListener("click", handleClick);

  // Cleanup when the listener is no longer needed
  return () => element.removeEventListener("click", handleClick);
}