package ru.me.a26preferencesframework;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class SeekBarDialogPreference extends DialogPreference {
    // Константы для значений по умолчанию для прогресса
    private static final int DEFAULT_MIN_PROGRESS = 0;
    private static final int DEFAULT_MAX_PROGRESS = 100;
    private static final int DEFAULT_PROGRESS = 0;

    // Поля для хранения минимального, максимального и текущего значения прогресса
    private int mMinProgress;
    private int mMaxProgress;
    private int mProgress;

    // Поля для пользовательского интерфейса: суффикс текста прогресса, текстовое поле и ползунок
    private CharSequence mProgressTextSuffix;
    private TextView mProgressText;
    private SeekBar mSeekBar;

    // Конструктор для программной инициализации
    public SeekBarDialogPreference(Context context) {
        this(context, null);
    }

    // Конструктор для инициализации с атрибутами из XML
    public SeekBarDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Извлечение настроек атрибутов из XML
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SeekBarDialogPreference, 0, 0);
        try {
            // Установка минимального, максимального значений и суффикса прогресса
            setMinProgress(a.getInteger(
                    R.styleable.SeekBarDialogPreference_min,
                    DEFAULT_MIN_PROGRESS));
            setMaxProgress(a.getInteger(
                    R.styleable.SeekBarDialogPreference_android_max,
                    DEFAULT_MAX_PROGRESS));
            setProgressTextSuffix(a
                    .getString(R.styleable.SeekBarDialogPreference_progressTextSuffix));
        } finally {
            a.recycle(); // Освобождение ресурсов
        }

        // Установка кастомного лэйаута для диалога
        setDialogLayoutResource(R.layout.preference_seek_bar_dialog);
        setPositiveButtonText(android.R.string.ok); // Кнопка "ОК"
        setNegativeButtonText(android.R.string.cancel); // Кнопка "Отмена"
        setDialogIcon(null); // Иконка диалога отключена
    }

    @Override
    protected void onSetInitialValue(boolean restore, Object defaultValue) {
        // Установка начального значения прогресса: восстановленного или дефолтного
        setProgress(restore ?
                getPersistedInt(DEFAULT_PROGRESS)
                : (Integer) defaultValue);
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        // Получение значения по умолчанию из XML
        return a.getInt(index, DEFAULT_PROGRESS);
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        // Настройка текстового сообщения диалога
        TextView dialogMessageText = (TextView) view
                .findViewById(R.id.text_dialog_message);
        dialogMessageText.setText(getDialogMessage());

        // Настройка отображения текущего значения прогресса
        mProgressText = (TextView) view.findViewById(R.id.text_progress);

        // Настройка слайдера (SeekBar)
        mSeekBar = (SeekBar) view.findViewById(R.id.seek_bar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Действие при завершении треккинга (не используется)
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Действие при начале треккинга (не используется)
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // Обновление текста отображения текущего значения прогресса
                String progressStr = String.valueOf(progress + mMinProgress);
                mProgressText.setText(mProgressTextSuffix == null ? progressStr
                        : progressStr.concat(mProgressTextSuffix.toString()));
            }
        });
        mSeekBar.setMax(mMaxProgress - mMinProgress); // Установка максимального значения для SeekBar
        mSeekBar.setProgress(mProgress - mMinProgress); // Установка текущего значения для SeekBar
    }

    // Получение минимального значения прогресса
    public int getMinProgress() {
        return mMinProgress;
    }

    // Установка минимального значения прогресса с проверкой текущего значения
    public void setMinProgress(int minProgress) {
        mMinProgress = minProgress;
        setProgress(Math.max(mProgress, mMinProgress));
    }

    // Получение максимального значения прогресса
    public int getMaxProgress() {
        return mMaxProgress;
    }

    // Установка максимального значения прогресса с проверкой текущего значения
    public void setMaxProgress(int maxProgress) {
        mMaxProgress = maxProgress;
        setProgress(Math.min(mProgress, mMaxProgress));
    }

    // Получение текущего значения прогресса
    public int getProgress() {
        return mProgress;
    }

    // Установка текущего значения прогресса с соблюдением границ диапазона
    public void setProgress(int progress) {
        progress = Math.max(Math.min(progress, mMaxProgress), mMinProgress);

        if (progress != mProgress) { // Обновление значения, если оно изменилось
            mProgress = progress;
            persistInt(progress); // Сохранение значения
            notifyChanged(); // Уведомление об изменении значения
        }
    }

    public CharSequence getProgressTextSuffix() {
        // Получение суффикса текста прогресса
        return mProgressTextSuffix;
    }

    public void setProgressTextSuffix(CharSequence progressTextSuffix) {
        // Установка суффикса текста прогресса
        mProgressTextSuffix = progressTextSuffix;
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        // Если подтверждено нажатием "ОК", сохраняем новое значение прогресса
        if (positiveResult) {
            int seekBarProgress = mSeekBar.getProgress() + mMinProgress;
            if (callChangeListener(seekBarProgress)) { // Проверка на возможность изменения значения
                setProgress(seekBarProgress); // Установка нового значения
            }
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        // Сохранение состояния при изменении ориентации и других событиях
        final Parcelable superState = super.onSaveInstanceState();

        // Создание объекта состояния
        final SavedState myState = new SavedState(superState);
        myState.minProgress = getMinProgress();
        myState.maxProgress = getMaxProgress();
        myState.progress = getProgress();

        return myState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        // Проверяем, сохранено ли состояние
        if (state == null || !state.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(state);
            return;
        }

        // Восстановление состояния
        SavedState myState = (SavedState) state;
        setMinProgress(myState.minProgress);
        setMaxProgress(myState.maxProgress);
        setProgress(myState.progress);

        super.onRestoreInstanceState(myState.getSuperState());
    }

    // Внутренний класс для сохранения состояния
    private static class SavedState extends BaseSavedState {
        int minProgress; // Сохранённое минимальное значение прогресса
        int maxProgress; // Сохранённое максимальное значение прогресса
        int progress; // Сохранённое текущее значение прогресса

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel source) {
            super(source);

            // Чтение сохранённых значений из Parcel
            minProgress = source.readInt();
            maxProgress = source.readInt();
            progress = source.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);

            // Запись значений в Parcel
            dest.writeInt(minProgress);
            dest.writeInt(maxProgress);
            dest.writeInt(progress);
        }

        @SuppressWarnings("unused")
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                // Создаём объект состояния из Parcel
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                // Создаем массив объектов состояния
                return new SavedState[size];
            }
        };
    }
}

