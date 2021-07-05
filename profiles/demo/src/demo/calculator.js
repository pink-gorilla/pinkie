

function removeAllChildNodes(parent) {
  while (parent.firstChild) {
      parent.removeChild(parent.firstChild);
  }
}

function addone (domnode, args) {
  var n = args.nr;
  var color = args.color
  var nr = n+1;

  var textnode = document.createTextNode("Result: " + nr); 

  // var pnode = document.createElement("p");      
  // pnode.appendChild(textnode); 

  removeAllChildNodes(domnode);
  domnode.style.backgroundColor = color;
  // domnode.appendChild(pnode); 
  domnode.appendChild(textnode);
}



module.exports.addone = addone;