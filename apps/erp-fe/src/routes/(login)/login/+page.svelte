<script lang="ts">
  import TextInput from '$lib/commons/TextInput.svelte'
  import PasswordInput from '$lib/commons/PasswordInput.svelte'
  import InputSection from '$lib/commons/InputSection.svelte'

  export let form

  let email = form !== undefined ? '' : form.email.val
  let password = form !== undefined ? '' : form.password.val
</script>

<div class="mx-auto mt-[10%]">
  <form method="POST">
    <InputSection
      name="Login"
      classes=" h-full w-full min-h-full flex-col gap-4"
    >
      <TextInput
        name="email"
        bind:value={email}
        placeholder="Email"
        classes=" bg-white text-black"
        error={!form ? undefined : form.email.message}
      />
      <PasswordInput
        name="password"
        bind:value={password}
        placeholder="Password"
        classes=" bg-white text-black"
        error={!form ? undefined : form.password.message}
      />
      <button class="btn btn-primary mx-auto">Login</button>
    </InputSection>
  </form>
</div>
