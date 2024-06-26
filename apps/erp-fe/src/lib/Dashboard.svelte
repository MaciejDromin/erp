<script>
  let items = [
    { label: 'Item 1', x: 0, y: 0 },
    { label: 'Item 2', x: 1, y: 0 },
    { label: 'Item 3', x: 2, y: 0 },
    { label: 'Item 4', x: 0, y: 1 },
    // ... other items with their respective coordinates
  ];

  const onDragStart = (event, item) => {
    event.dataTransfer.setData('text/plain', JSON.stringify({ label: item.label, x: item.x, 
y: item.y }));
    event.target.classList.add('is-moving');
  };

  const onDrop = (event, targetColumn) => {
    const data = JSON.parse(event.dataTransfer.getData('text'));
    items[targetColumn].label = data.label;
    items[targetColumn].x = data.x;
    items[targetColumn].y = data.y;
    updateItems();
    event.target.classList.remove('is-moving');
  };

  const onDragOver = (event) => {
    event.preventDefault()
    const targetRow = event.currentTarget.closest('.grid-row').dataset.row;
    const targetColumn = Number(event.target.parentNode.parentNode.dataset.column);
    const placeholder = document.createElement('div');
    document.querySelectorAll('.placeholder').forEach((placeHolder) => {
        placeHolder.remove();
    });
    placeholder.classList.add('placeholder');
    placeholder.style.gridColumnStart = targetColumn + 1;
    placeholder.style.gridRowStart = Number(targetRow) + 1;
    event.target.parentNode.append(placeholder);  };

  const updateItems = () => {
    // Update the items array based on user drag-and-drop actions
    console.log('items:', items);
  };
</script>

<div class="flex flex-wrap dnd-grid">
  {#each items as item, index (index)}
    <div draggable={true} on:dragstart={() => onDragStart(event, item)} on:drop={() => 
onDrop(event, index)} on:dragover={onDragOver} class="drag-item" style={`grid-column: 
${item.x + 1}; grid-row: ${item.y + 1}`}>
      {item.label}
    </div>
  {/each}
</div>
<style>
.dnd-grid {
  display: grid;
  grid-template-columns: auto auto auto;
  gap: 20px;
}

.drag-item {
  border: 1px solid #ccc;
  padding: 10px;
  margin-bottom: 10px;
  cursor: move;
  transition: transform 0.3s ease-in-out;
}

.drag-item:hover {
  background-color: #f5f5f5;
}

.is-moving {
  will-change: transform;
}

.placeholder {
  background-color: #ddd;
  border: 1px solid #ddd;
  opacity: 0.5;
}
</style>
