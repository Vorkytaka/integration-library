package ru.evotor.framework.payment

import android.os.Parcel
import android.os.Parcelable
import java.math.BigDecimal

/**
 * Признак способа расчета для позиции
 */
sealed class PaymentFeature : Parcelable {

    companion object {
        enum class Type {
            /**
             * Полная предварительная оплата до момента передачи предмета расчета
             */
            PREPAYMENT_FULL,
            /**
             * Частичная предварительная оплата до момента передачи предмета расчета
             */
            PREPAYMENT_PARTIAL,
            /**
             * Аванс
             */
            ADVANCE,
            /**
             * Полная оплата, в том числе с учетом аванса (предварительной оплаты) в момент передачи предмета расчета
             */
            CHECKOUT_FULL,
            /**
             * Частичная оплата предмета расчета в момент его передачи с последующей оплатой в кредит
             */
            CHECKOUT_PARTIAL,
            /**
             * Передача предмета расчета без его оплаты в момент его передачи с последующей оплатой в кредит
             */
            CREDIT_PASS,
            /**
             * Оплата предмета расчета после его передачи с оплатой в кредит (оплата кредита)
             */
            CREDIT_CHECKOUT
        }

        @JvmStatic
        @JvmOverloads
        fun fromType(type: Type, amount: BigDecimal? = null): PaymentFeature = when (type) {
            PaymentFeature.Companion.Type.PREPAYMENT_FULL -> PrepaymentFull()
            PaymentFeature.Companion.Type.PREPAYMENT_PARTIAL -> PrepaymentPartial()
            PaymentFeature.Companion.Type.ADVANCE -> Advance()
            PaymentFeature.Companion.Type.CHECKOUT_FULL -> CheckoutFull()
            PaymentFeature.Companion.Type.CHECKOUT_PARTIAL -> CheckoutPartial(amount!!)
            PaymentFeature.Companion.Type.CREDIT_PASS -> CreditPass()
            PaymentFeature.Companion.Type.CREDIT_CHECKOUT -> CreditCheckout()
        }

        @JvmStatic
        @JvmOverloads
        fun fromInt(typeOrdinal: Int, amount: BigDecimal? = null): PaymentFeature {
            val type = Type.values()[typeOrdinal]
            return fromType(type)
        }
    }

    fun toInt(): Int = when (this) {
        is PaymentFeature.PrepaymentFull -> Type.PREPAYMENT_FULL.ordinal
        is PaymentFeature.PrepaymentPartial -> Type.PREPAYMENT_PARTIAL.ordinal
        is PaymentFeature.Advance -> Type.ADVANCE.ordinal
        is PaymentFeature.CheckoutFull -> Type.CHECKOUT_FULL.ordinal
        is PaymentFeature.CheckoutPartial -> Type.CHECKOUT_PARTIAL.ordinal
        is PaymentFeature.CreditPass -> Type.CREDIT_PASS.ordinal
        is PaymentFeature.CreditCheckout -> Type.CREDIT_CHECKOUT.ordinal
    }

    protected abstract fun writeFieldsToParcel(dest: Parcel, flags: Int)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        // Determine position in parcel for writing data size
        val dataSizePosition = parcel.dataPosition()
        // Use integer placeholder for data size
        parcel.writeInt(0)
        //Determine position of data start
        val startDataPosition = parcel.dataPosition()

        writeFieldsToParcel(parcel, flags)

        // Calculate data size
        val dataSize = parcel.dataPosition() - startDataPosition
        // Save position at the end of data
        val endOfDataPosition = parcel.dataPosition()
        //Set position to start to write additional data size
        parcel.setDataPosition(dataSizePosition)
        parcel.writeInt(dataSize)
        // Go back to the end of parcel
        parcel.setDataPosition(endOfDataPosition)
    }

    /**
     * Полная предварительная оплата до момента передачи предмета расчета
     */
    class PrepaymentFull() : PaymentFeature() {

        override fun writeFieldsToParcel(dest: Parcel, flags: Int) {

        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(VERSION)
            super.writeToParcel(parcel, flags)
        }

        override fun describeContents(): Int = 0

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is PrepaymentFull) return false
            return true
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }

        companion object {
            private const val VERSION = 1

            @JvmStatic
            private fun readFromParcel(parcel: Parcel): PrepaymentFull {
                val version = parcel.readInt()
                val dataSize = parcel.readInt()
                val dataStartPosition = parcel.dataPosition()
                // read fields here
                parcel.setDataPosition(dataStartPosition + dataSize)
                return PrepaymentFull()
            }

            @JvmField
            val CREATOR: Parcelable.Creator<PrepaymentFull> = object : Parcelable.Creator<PrepaymentFull> {
                override fun createFromParcel(parcel: Parcel): PrepaymentFull = readFromParcel(parcel)

                override fun newArray(size: Int): Array<PrepaymentFull?> = arrayOfNulls(size)
            }
        }
    }

    /**
     * Частичная предварительная оплата до момента передачи предмета расчета
     */
    class PrepaymentPartial() : PaymentFeature() {

        override fun writeFieldsToParcel(dest: Parcel, flags: Int) {

        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(VERSION)
            super.writeToParcel(parcel, flags)
        }

        override fun describeContents(): Int = 0

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is PrepaymentPartial) return false
            return true
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }

        companion object {
            private const val VERSION = 1

            @JvmStatic
            private fun readFromParcel(parcel: Parcel): PrepaymentPartial {
                val version = parcel.readInt()
                val dataSize = parcel.readInt()
                val dataStartPosition = parcel.dataPosition()
                // read fields here
                parcel.setDataPosition(dataStartPosition + dataSize)
                return PrepaymentPartial()
            }

            @JvmField
            val CREATOR: Parcelable.Creator<PrepaymentPartial> = object : Parcelable.Creator<PrepaymentPartial> {
                override fun createFromParcel(parcel: Parcel): PrepaymentPartial = readFromParcel(parcel)

                override fun newArray(size: Int): Array<PrepaymentPartial?> = arrayOfNulls(size)
            }
        }
    }

    /**
     * Аванс
     */
    class Advance() : PaymentFeature() {

        override fun writeFieldsToParcel(dest: Parcel, flags: Int) {

        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(VERSION)
            super.writeToParcel(parcel, flags)
        }

        override fun describeContents(): Int = 0

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Advance) return false
            return true
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }

        companion object {
            private const val VERSION = 1

            @JvmStatic
            private fun readFromParcel(parcel: Parcel): Advance {
                val version = parcel.readInt()
                val dataSize = parcel.readInt()
                val dataStartPosition = parcel.dataPosition()
                // read fields here
                parcel.setDataPosition(dataStartPosition + dataSize)
                return Advance()
            }

            @JvmField
            val CREATOR: Parcelable.Creator<Advance> = object : Parcelable.Creator<Advance> {
                override fun createFromParcel(parcel: Parcel): Advance = readFromParcel(parcel)

                override fun newArray(size: Int): Array<Advance?> = arrayOfNulls(size)
            }
        }
    }

    /**
     * Полная оплата, в том числе с учетом аванса (предварительной оплаты) в момент передачи предмета расчета
     *
     * По-умолчанию
     */
    class CheckoutFull() : PaymentFeature() {

        override fun writeFieldsToParcel(dest: Parcel, flags: Int) {

        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(VERSION)
            super.writeToParcel(parcel, flags)
        }

        override fun describeContents(): Int = 0

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is CheckoutFull) return false
            return true
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }

        companion object {
            private const val VERSION = 1

            @JvmStatic
            private fun readFromParcel(parcel: Parcel): CheckoutFull {
                val version = parcel.readInt()
                val dataSize = parcel.readInt()
                val dataStartPosition = parcel.dataPosition()
                // read fields here
                parcel.setDataPosition(dataStartPosition + dataSize)
                return CheckoutFull()
            }

            @JvmField
            val CREATOR: Parcelable.Creator<CheckoutFull> = object : Parcelable.Creator<CheckoutFull> {
                override fun createFromParcel(parcel: Parcel): CheckoutFull = readFromParcel(parcel)

                override fun newArray(size: Int): Array<CheckoutFull?> = arrayOfNulls(size)
            }
        }
    }

    /**
     * Частичная оплата предмета расчета в момент его передачи с последующей оплатой в кредит
     *
     * @param amount : сумма первичного взноса
     */
    class CheckoutPartial(val amount: BigDecimal) : PaymentFeature() {

        override fun writeFieldsToParcel(dest: Parcel, flags: Int) {
            dest.writeString(amount.toPlainString())
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(VERSION)
            super.writeToParcel(parcel, flags)
        }

        override fun describeContents(): Int = 0

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is CheckoutPartial) return false

            if (amount != other.amount) return false

            return true
        }

        override fun hashCode(): Int {
            return amount.hashCode()
        }

        companion object {
            private const val VERSION = 1

            @JvmStatic
            private fun readFromParcel(parcel: Parcel): CheckoutPartial {
                val version = parcel.readInt()
                val dataSize = parcel.readInt()
                val dataStartPosition = parcel.dataPosition()
                // read fields here
                val amount = BigDecimal(parcel.readString())
                parcel.setDataPosition(dataStartPosition + dataSize)
                return CheckoutPartial(amount)
            }

            @JvmField
            val CREATOR: Parcelable.Creator<CheckoutPartial> = object : Parcelable.Creator<CheckoutPartial> {
                override fun createFromParcel(parcel: Parcel): CheckoutPartial = readFromParcel(parcel)

                override fun newArray(size: Int): Array<CheckoutPartial?> = arrayOfNulls(size)
            }
        }
    }

    /**
     * Передача предмета расчета без его оплаты в момент его передачи с последующей оплатой в кредит
     */
    class CreditPass() : PaymentFeature() {

        override fun writeFieldsToParcel(dest: Parcel, flags: Int) {

        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(VERSION)
            super.writeToParcel(parcel, flags)
        }

        override fun describeContents(): Int = 0

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is CreditPass) return false
            return true
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }

        companion object {
            private const val VERSION = 1

            @JvmStatic
            private fun readFromParcel(parcel: Parcel): CreditPass {
                val version = parcel.readInt()
                val dataSize = parcel.readInt()
                val dataStartPosition = parcel.dataPosition()
                // read fields here
                parcel.setDataPosition(dataStartPosition + dataSize)
                return CreditPass()
            }

            @JvmField
            val CREATOR: Parcelable.Creator<CreditPass> = object : Parcelable.Creator<CreditPass> {
                override fun createFromParcel(parcel: Parcel): CreditPass = readFromParcel(parcel)

                override fun newArray(size: Int): Array<CreditPass?> = arrayOfNulls(size)
            }
        }
    }

    /**
     * Оплата предмета расчета после его передачи с оплатой в кредит (оплата кредита)
     */
    class CreditCheckout() : PaymentFeature() {

        override fun writeFieldsToParcel(dest: Parcel, flags: Int) {

        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(VERSION)
            super.writeToParcel(parcel, flags)
        }

        override fun describeContents(): Int = 0

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is CreditCheckout) return false
            return true
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }

        companion object {
            private const val VERSION = 1

            @JvmStatic
            private fun readFromParcel(parcel: Parcel): CreditCheckout {
                val version = parcel.readInt()
                val dataSize = parcel.readInt()
                val dataStartPosition = parcel.dataPosition()
                // read fields here
                parcel.setDataPosition(dataStartPosition + dataSize)
                return CreditCheckout()
            }

            @JvmField
            val CREATOR: Parcelable.Creator<CreditCheckout> = object : Parcelable.Creator<CreditCheckout> {
                override fun createFromParcel(parcel: Parcel): CreditCheckout = readFromParcel(parcel)

                override fun newArray(size: Int): Array<CreditCheckout?> = arrayOfNulls(size)
            }
        }
    }

}