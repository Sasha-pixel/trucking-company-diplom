package com.example.truck.business.service.util;

import lombok.experimental.UtilityClass;

import com.example.truck.business.service.util.DrillDownUtils.ScreenViews;
import com.example.truck.business.service.util.DrillDownUtils.Screens;
import com.example.truck.business.service.util.DrillDownUtils.Views;
import io.tesler.core.crudma.bc.BcIdentifier;
import io.tesler.core.dto.DrillDownType;
import io.tesler.core.dto.rowmeta.PostAction;

import static com.example.truck.business.service.util.DrillDownUtils.bcUrl;
import static com.example.truck.business.service.util.DrillDownUtils.viewUrl;
import static io.tesler.core.dto.DrillDownType.INNER;

@UtilityClass
public class CustomPostAction {

	/**
	 * Дополнительное действие (происходящее после основного действия по кнопке), осуществляющее переход по ссылке внутри приложения в текущей вкладке
	 *
	 * @param url ссылка, куда будет осуществлён переход
	 * @return Дополнительное действие {@link PostAction.BasePostActionType#DRILL_DOWN} с типом {@link DrillDownType#INNER}
	 */
	public static PostAction innerDrillDown(final String url) {
		return PostAction.drillDown(INNER, url);
	}

	/**
	 * Дополнительное действие (происходящее после основного действия по кнопке), осуществляющее переход по ссылке внутри приложения в текущей вкладке
	 * <p>(переход на запись на виджете)
	 *
	 * @param screenName наименование экрана, куда будет осуществлён переход
	 * @param viewName наименование представления, куда будет осуществлён переход
	 * @param bc бизнес-компонента, на которую будет осуществлён переход
	 * @param id id-записи на виджете с данной bc
	 * @return Дополнительное действие {@link PostAction.BasePostActionType#DRILL_DOWN} с типом {@link DrillDownType#INNER}
	 */
	public static PostAction innerDrillDown(final Screens screenName, final Views viewName, final BcIdentifier bc,
																					final Long id) {
		return innerDrillDown(bcUrl(screenName, viewName, bc, id));
	}

	/**
	 * Дополнительное действие (происходящее после основного действия по кнопке), осуществляющее переход по ссылке внутри приложения в текущей вкладке
	 * <p>(переход на запись на виджете)
	 *
	 * @param screenViewName наименование экрана и представления, куда будет осуществлён переход
	 * @param bc бизнес-компонента, на которую будет осуществлён переход
	 * @param id id-записи на виджете с данной bc
	 * @return Дополнительное действие {@link PostAction.BasePostActionType#DRILL_DOWN} с типом {@link DrillDownType#INNER}
	 */
	public static PostAction innerDrillDown(final ScreenViews screenViewName, final BcIdentifier bc, final Long id) {
		return innerDrillDown(bcUrl(screenViewName, bc, id));
	}

	/**
	 * Дополнительное действие (происходящее после основного действия по кнопке), осуществляющее переход по ссылке внутри приложения в текущей вкладке
	 * <p>(переход на представление на экране)
	 *
	 * @param screenName наименование экрана, куда будет осуществлён переход
	 * @param viewName наименование представления, куда будет осуществлён переход
	 * @return Дополнительное действие {@link PostAction.BasePostActionType#DRILL_DOWN} с типом {@link DrillDownType#INNER}
	 */
	public static PostAction innerDrillDown(final Screens screenName, final Views viewName) {
		return innerDrillDown(viewUrl(screenName, viewName));
	}

	/**
	 * Дополнительное действие (происходящее после основного действия по кнопке), осуществляющее переход по ссылке внутри приложения в текущей вкладке
	 * <p>(переход на представление на экране)
	 *
	 * @param screenViewName наименование экрана и представления, куда будет осуществлён переход
	 * @return Дополнительное действие {@link PostAction.BasePostActionType#DRILL_DOWN} с типом {@link DrillDownType#INNER}
	 */
	public static PostAction innerDrillDown(final ScreenViews screenViewName) {
		return innerDrillDown(viewUrl(screenViewName));
	}

}
