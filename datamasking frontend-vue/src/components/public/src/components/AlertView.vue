<template>
<div>
    <div :class="`alert alert-${alertType}`" role="alert">
        {{ alertMsg }}
    </div>
</div>
</template>

<script>
export default {
    props: ["alertType", "alertMsg"],
    setup() {
        return {};
    },
};
</script>

<style lang="scss" scoped>
</style>
