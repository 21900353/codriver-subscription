<template>
    <v-card outlined>
        <v-card-title>
            Pay
        </v-card-title>

        <v-card-text>
            <Number label="SubId" v-model="value.subId" :editMode="editMode"/>
            <Number label="UserId" v-model="value.userId" :editMode="editMode"/>
            <Boolean label="SubStatus" v-model="value.subStatus" :editMode="editMode"/>
            <String label="Date" v-model="value.date" :editMode="editMode"/>
            <String label="SubProcessed" v-model="value.subProcessed" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="primary"
                    text
                    @click="pay"
            >
                Pay
            </v-btn>
            
            <v-btn
                    color="primary"
                    text
                    @click="close"
            >
                Close
            </v-btn>
        </v-card-actions>
    </v-card>

</template>

<script>
   
    export default {
        name: 'PayCommand',
        components:{},
        props: {},
        data: () => ({
            editMode: true,
            value: {},
        }),
        created() {
            this.value.subId = 0;
            this.value.userId = 0;
            this.value.subStatus = false;
            this.value.date = '';
            this.value.subProcessed = '';
        },
        watch: {
        },
        methods: {
            pay() {
                this.$emit('pay', this.value);
            },
            close() {
                this.$emit('closeDialog');
            },
            change() {
                this.$emit('input', this.value);
            },
        }
    }
</script>

