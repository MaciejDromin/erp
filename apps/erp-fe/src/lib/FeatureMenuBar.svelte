<script>
  import { goto } from '$app/navigation'

  export let config

  const gotoAdd = () => {
    goto(config.addButton.url)
  }

</script>

<div class="navbar bg-base-100 rounded shadow-xl">
  <div class="flex-1">
    <a class="p-4 font-bold text-xl">{config.title}</a>
  </div>
  <div class="flex-none">
    <ul class="menu menu-horizontal px-1">
      <li>
        <button on:click={gotoAdd} class="btn btn-primary mr-3">Add</button>
      </li>
      <li>
        <button disabled={config.editButton.disabled} class="btn btn-primary mr-3">Edit</button>
      </li>
      <li><button disabled={config.deleteButton.disabled} class="btn btn-primary">Delete</button></li>
    </ul>
  </div>
</div>
