<script>
  // TODO: get items on initial dashboard open
  let items = [
    { label: 'Item 1', x: 0, y: 0 },
    { label: 'Item 4', x: 0, y: 1 },
    { label: 'Item 2', x: 1, y: 0 },
    { label: 'Item 3', x: 2, y: 0 },
    // ... other items with their respective coordinates
  ];

  const onDragStart = (event, item) => {
    event.dataTransfer.setData('text/plain', JSON.stringify({ label: item.label, x: item.x, 
y: item.y }));
  };

  const onDrop = (event) => {
    const data = JSON.parse(event.dataTransfer.getData('text'));
    const targetAttr = event.target.attributes;
    const targetRow = Number(targetAttr.y.value);
    const targetColumn = Number(targetAttr.x.value);
    items.forEach((item) => {
      if (item.label === data.label) {
        item.x = targetColumn - 1
        item.y = targetRow - 1
      }
    })
    items = items;
    updateItems();
    document.querySelectorAll('.placeholder').forEach((placeHolder) => {
        placeHolder.remove();
    });
  };

  const onDragOver = (event) => {
    event.preventDefault()
    const targetStyle = event.target.attributes.style.value;
    const targetRow = targetStyle.charAt(targetStyle.length - 1);
    const targetColumn = targetStyle.charAt(targetStyle.indexOf(";") - 1);
    dropPlaceholder(targetRow, targetColumn)
  };

  const dropPlaceholder = (targetRow, targetColumn) => {
    const placeholder = document.createElement('div');
    document.querySelectorAll('.placeholder').forEach((placeHolder) => {
        placeHolder.remove();
    });
    placeholder.classList.add('placeholder');
    placeholder.style.cssText = `grid-column: ${targetColumn}; grid-row: ${targetRow}`
    placeholder.style.border = "4px solid red";
    placeholder.style.opacity = "0.5";
    placeholder.setAttribute('x', targetColumn)
    placeholder.setAttribute('y', targetRow)
    placeholder.addEventListener("drop", function(e) {
      onDrop(e)
    })
    placeholder.addEventListener("dragover", function(e) {
      e.preventDefault();
    })

    event.target.parentNode.append(placeholder);
  }

  const updateItems = () => {
    // Update the items array based on user drag-and-drop actions
    console.log('items:', items);
    // TODO: here update items locations
    // TODO: at the end send new locations to backend
  };
</script>

<div class="flex flex-wrap dnd-grid">
  {#each items as item}
    <div draggable={true} on:dragstart={() => onDragStart(event, item)} on:drop={() => 
onDrop(event)} on:dragover={onDragOver} class="drag-item" style={`grid-column: 
${item.x + 1}; grid-row: ${item.y + 1}`}>
      {item.label}
    </div>
  {/each}
</div>
<style global>
.dnd-grid {
  display: grid;
  grid-template-columns: 32% 32% 32%;
  gap: 20px;
}

.drag-item {
  border: 1px solid #ccc;
  padding: 10px;
  cursor: move;
  transition: transform 0.3s ease-in-out;
}

.drag-item:hover {
  background-color: #f5f5f5;
}
</style>
