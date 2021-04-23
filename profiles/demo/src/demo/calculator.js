

function removeAllChildNodes(parent) {
  while (parent.firstChild) {
      parent.removeChild(parent.firstChild);
  }
}

function addone (domnode, n) {
  var nr = n+1;
  domnode.appendChild
  var node = document.createElement("p");           
  var textnode = document.createTextNode("Result: " + nr);      
  node.appendChild(textnode);                    
  removeAllChildNodes(domnode);
  domnode.appendChild(node); 
}



module.exports.addone = addone;