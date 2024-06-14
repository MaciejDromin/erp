<script>
  import { enhance } from '$app/forms';
</script>
<div id="currency" class="flex flex-col gap-3 px-10 pt-10">
  <form method="POST" use:enhance enctype="multipart/form-data" class="mx-auto flex flex-col gap-3 py-6">
    <input type="file" name="file" required class="file-input file-input-bordered w-full max-w-xs" />
    <button class="btn btn-primary mx-auto">Scan Purchases</button>
  </form>
</div>
