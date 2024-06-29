<script>
  // TODO: get items on initial dashboard open
  
  export let data

  let items = data.widgets
  
  const onDragStart = (event, item) => {
    event.dataTransfer.setData('text/plain', JSON.stringify(item));
  };

  const onDrop = (event) => {
    const data = JSON.parse(event.dataTransfer.getData('text'));
    const targetAttr = event.target.attributes;
    const targetRow = Number(targetAttr.y.value);
    const targetColumn = Number(targetAttr.x.value);
    updateItems(data, targetColumn - 1, targetRow - 1);
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
    placeholder.style.border = "4px solid #b387fa";
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

  const updateItems = (data, targetColumn, targetRow) => {
    // Update the items array based on user drag-and-drop actions
    if (isNeighbour(data.position.x, data.position.y, targetColumn, targetRow)) {
      items.forEach((item) => {
        if (item.id === data.id) {
          item.position.x = targetColumn
          item.position.y = targetRow
          return;
        }
        if (item.position.x === targetColumn && item.position.y === targetRow) {
          item.position.x = data.position.x;
          item.position.y = data.position.y;
          return;
        }
      })
      items = items;
      return;
    }
    if ((targetRow === data.position.y && data.position.x < targetColumn)
      || targetRow > data.position.y) {
      items.forEach((item) => {
        if (item.id === data.id) {
          item.position.x = targetColumn
          item.position.y = targetRow
          return;
        }
        if (item.position.x === targetColumn && item.position.y === targetRow) {
          item.position.x = data.position.x;
          item.position.y = data.position.y;
          return;
        }
      })
      items = items;
      return;
    }
    items.forEach((item) => {
      console.log("here")
      if (item.id === data.id) {
        item.position.x = targetColumn
        item.position.y = targetRow
        return;
      }
      if (item.position.y === targetRow && item.position.x === targetColumn) {
        if (isNeighbour(item.position.x, item.position.y, data.position.x, data.position.y)) {
          item.position.x = data.position.x;
          item.position.y = data.position.y;
          return;
        }
        const position = determineNewPosition(item.position.y, item.position.x);
        item.position.x = position.x;
        item.position.y = position.y;
        return;
      }
      if (isBetween({ x: item.position.x, y: item.position.y },
          positionsInOrder(data.position.x, data.position.y, targetColumn, targetRow))) {
        const position = determineNewPosition(item.position.y, item.position.x);
        item.position.x = position.x;
        item.position.y = position.y;
      }
    })
    items = items;
    // TODO: at the end send new locations to backend
  };

  const positionsInOrder = (dataX, dataY, targetColumn, targetRow) => {
    if (dataY === targetRow) {
      const xOrder = dataX > targetColumn ? { start: targetColumn, end: dataX }
        : { start: dataX, end: targetColumn }
      return { 
        start: { x: xOrder.start, y: targetRow },
        end: { x: xOrder.end, y: targetRow }
      }
    }
    if (targetRow > dataY) {
      return {
        start: { x: dataX, y: dataY },
        end: { x: targetColumn, y: targetRow }
      }
    }
    return {
      start: { x: targetColumn, y: targetRow },
      end: { x: dataX, y: dataY }
    }
  }

  const isBetween = (itemPos, pointsPos) => {
    if (itemPos.y > pointsPos.end.y) return false;
    if (itemPos.y < pointsPos.start.y) return false;
    if (itemPos.y === pointsPos.start.y) {
      if (itemPos.x >= pointsPos.start.x) return true;
      return false;
    }
    if (itemPos.y === pointsPos.end.y) {
      if (itemPos.x <= pointsPos.end.x) return true;
      return false;
    }
    return true;
  }

  const isNeighbour = (itemX, itemY, targetX, targetY) => {
    if (itemY === targetY && itemX + 1 === targetX) return true;
    if (itemY === targetY && itemX - 1 === targetX) return true;
    if (itemX === 0 && itemY - 1 === targetY && 2 === targetX) return true;
    if (itemX === 2 && itemY + 1 === targetY && 0 === targetX) return true;
    return false;
  }

  const determineNewPosition = (itemY, itemX) => {
    if (itemX + 1 >= 3) {
      return { x: 0, y: itemY + 1 }
    }
    return { x: itemX + 1, y: itemY }
  }
</script>

<div class="flex flex-wrap dnd-grid">
  {#each items as item}
    <div draggable={true} on:dragstart={() => onDragStart(event, item)} on:drop={() => 
      onDrop(event)} on:dragover={onDragOver} class="drag-item" style={`grid-column: 
      ${item.position.x + 1}; grid-row: ${item.position.y + 1}`}>
      {item.name}
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
