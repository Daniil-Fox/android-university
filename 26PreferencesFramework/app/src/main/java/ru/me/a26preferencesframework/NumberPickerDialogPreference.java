package ru.me.a26preferencesframework;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

public class NumberPickerDialogPreference extends DialogPreference {
    private static final int DEFAULT_MIN_VALUE = 0;
    private static final int DEFAULT_MAX_VALUE = 100;
    private static final int DEFAULT_VALUE = 0;

    // Поля для хранения значений диапазона и текущего значения
    private int mMinValue;
    private int mMaxValue;
    private int mValue;
    private NumberPicker mNumberPicker;

    // Конструктор для программного создания экземпляра
    public NumberPickerDialogPreference(Context context)
    {
        this(context, null);
    }

    // Конструктор с атрибутами из XML
    public NumberPickerDialogPreference(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        // Получение значений атрибутов из XML
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SeekBarDialogPreference, 0, 0);
        try
        {
            // Установка минимального и максимального значений
            setMinValue(a.getInteger(R.styleable.SeekBarDialogPreference_min, DEFAULT_MIN_VALUE));
            setMaxValue(a.getInteger(R.styleable.SeekBarDialogPreference_android_max, DEFAULT_MAX_VALUE));
        }
        finally
        {
            a.recycle(); // Освобождение ресурсов
        }

        // Установка кастомного лэйаута для диалога
        setDialogLayoutResource(R.layout.preference_number_picker_dialog);
        setPositiveButtonText(android.R.string.ok); // Текст кнопки "ОК"
        setNegativeButtonText(android.R.string.cancel); // Текст кнопки "Отмена"
        setDialogIcon(null); // Иконка диалога отключена
    }

    @Override
    protected void onSetInitialValue(boolean restore, Object defaultValue)
    {
        // Установка начального значения: либо восстановленного, либо дефолтного
        setValue(restore ? getPersistedInt(DEFAULT_VALUE) : (Integer) defaultValue);
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index)
    {
        // Получение значения по умолчанию из XML
        return a.getInt(index, DEFAULT_VALUE);
    }

    @Override
    protected void onBindDialogView(View view)
    {
        super.onBindDialogView(view);

        // Установка текста сообщения диалога
        TextView dialogMessageText = (TextView) view.findViewById(R.id.text_dialog_message);
        dialogMessageText.setText(getDialogMessage());

        // Настройка NumberPicker
        mNumberPicker = (NumberPicker) view.findViewById(R.id.number_picker);
        mNumberPicker.setMinValue(mMinValue); // Установка минимального значения
        mNumberPicker.setMaxValue(mMaxValue); // Установка максимального значения
        mNumberPicker.setValue(mValue); // Установка текущего значения
    }

    // Получить минимальное значение
    public int getMinValue()
    {
        return mMinValue;
    }

    // Установить минимальное значение, обновляя текущее значение если нужно
    public void setMinValue(int minValue)
    {
        mMinValue = minValue;
        setValue(Math.max(mValue, mMinValue)); // Проверка текущего значения
    }

    // Получить максимальное значение
    public int getMaxValue()
    {
        return mMaxValue;
    }

    // Установить максимальное значение, обновляя текущее значение если нужно
    public void setMaxValue(int maxValue)
    {
        mMaxValue = maxValue;
        setValue(Math.min(mValue, mMaxValue)); // Проверка текущего значения
    }

    // Получить текущее значение
    public int getValue()
    {
        return mValue;
    }

    // Установить текущее значение с проверкой на диапазон
    public void setValue(int value)
    {
        value = Math.max(Math.min(value, mMaxValue), mMinValue); // Ограничение значением диапазона

        if (value != mValue) // Если значение изменилось
        {
            mValue = value;
            persistInt(value); // Сохранение значения
            notifyChanged(); // Уведомление об изменении
        }
    }

    @Override
    protected void onDialogClosed(boolean positiveResult)
    {
        super.onDialogClosed(positiveResult);

        // Если пользователь нажал "ОК"
        if (positiveResult)
        {
            int numberPickerValue = mNumberPicker.getValue();
            if (callChangeListener(numberPickerValue)) // Проверка на возможность изменения
            {
                setValue(numberPickerValue); // Установка нового значения
            }
        }
    }

    @Override
    protected Parcelable onSaveInstanceState()
    {
        // Сохранение состояния для выживания при изменении ориентации экрана или других событиях
        final Parcelable superState = super.onSaveInstanceState();

        // Сохранение текущих значений
        final SavedState myState = new SavedState(superState);
        myState.minValue = getMinValue();
        myState.maxValue = getMaxValue();
        myState.value = getValue();

        return myState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state)
    {
        // Проверка, сохранялось ли состояние
        if (state == null || !state.getClass().equals(SavedState.class))
        {
            super.onRestoreInstanceState(state); // Если нет, вызов супер-класса
            return;
        }

        // Восстановление состояния
        SavedState myState = (SavedState) state;
        setMinValue(myState.minValue);
        setMaxValue(myState.maxValue);
        setValue(myState.value);

        super.onRestoreInstanceState(myState.getSuperState());
    }

    // Сохранение и восстановление состояния
    private static class SavedState extends BaseSavedState
    {
        int minValue; // Минимальное значение
        int maxValue; // Максимальное значение
        int value; // Текущее сохранённое значение

        // Конструктор для сохранения суперкласса
        public SavedState(Parcelable superState)
        {
            super(superState);
        }

        // Чтение сохранённого состояния
        public SavedState(Parcel source)
        {
            super(source);

            minValue = source.readInt();
            maxValue = source.readInt();
            value = source.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags)
        {
            super.writeToParcel(dest, flags);

            // Сохранение значений
            dest.writeInt(minValue);
            dest.writeInt(maxValue);
            dest.writeInt(value);
        }

        @SuppressWarnings("unused")
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>()
        {
            @Override
            public SavedState createFromParcel(Parcel in)
            {
                return new SavedState(in); // Создание состояния из Parcel
            }

            @Override
            public SavedState[] newArray(int size)
            {
                return new SavedState[size]; // Массив для хранения состояний
            }
        };
    }
}
